package com.example.springai.controller;


import com.example.springai.entity.ActorsFilms;
import com.example.springai.entity.dto.ChatDTO;
import com.example.springai.service.ChatService;
import com.example.springai.utils.JacksonUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jillong
 * @date 2024/6/6
 */
@Log4j2
@RestController
@RequestMapping("/ai/chat")
public class ChatController {

    @Resource
    private OpenAiChatClient chatClient;

    @Resource
    private ChatService chatService;


    /**
     * 非流式输出 call：等待大模型把回答结果全部生成后输出给用户；
     *
     * @param chatDTO 用户输入的消息
     * @return 生成的对话
     */
    @PostMapping
    public String chat(@RequestBody ChatDTO chatDTO) {
        return chatService.getModelResponse(chatDTO);
    }

    /**
     * 流式输出 stream：逐个字符输出，一方面符合大模型生成方式的本质，另一方面当模型推理效率不是很高时，
     * 流式输出比起全部生成后再输出大大提高用户体验。
     *
     * @param message 用户输入的消息
     * @return 生成的对话
     */
    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }


    /**
     * 用提示词模板，生成一个旅游助手
     *
     * @param name  机器人名字
     * @param voice 机器人风格 voice=幽默
     * @return 生成的对话
     */
    @GetMapping("/prompt")
    public String prompt(@RequestParam(value = "name") String name,
                         @RequestParam(value = "voice") String voice) {
        String userText = """
                给我推荐上海的至少三个旅游景点
                """;

        Message userMessage = new UserMessage(userText);

        String systemText = """
                你是一个有用的人工智能助手，可以帮助人们查找信息，
                你的名字是{name}，
                你应该用你的名字和{voice}的风格回复用户的请求。
                """;

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        List<Generation> response = chatClient.call(prompt).getResults();
        StringBuilder result = new StringBuilder();
        for (Generation generation : response) {
            String content = generation.getOutput().getContent();
            result.append(content);
        }
        return result.toString();
    }

    /**
     * 用函数调用模板，生成一个天气助手
     *
     * @param message 用户输入的消息
     * @return 生成的对话
     */
    @GetMapping("/generate/function/call")
    public String functionCall(@RequestParam(value = "message", defaultValue = "上海天气如何?") String message) {

        String systemPrompt = "{prompt}";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("prompt", "你是一个有用的人工智能助手"));

        Message userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage),
                OpenAiChatOptions.builder().withFunctions(Set.of("currentWeather", "currentPopulation")).build());

        List<Generation> response = chatClient.call(prompt).getResults();
        StringBuilder result = new StringBuilder();
        for (Generation generation : response) {
            String content = generation.getOutput().getContent();
            result.append(content);
        }

        return JacksonUtil.ObjectToJson(result.toString());
    }

    /**
     * 结构化输出: 将大模型的输出直接映射为我们期望的 Java 类 ActorsFilms 的对象
     *
     * @param actor 演员名字
     * @return 生成的电影作品年表
     */
    @GetMapping("/parser")
    public String Response(@RequestParam(value = "actor") String actor) {
        BeanOutputParser<ActorsFilms> outputParser = new BeanOutputParser<>(ActorsFilms.class);

        String userMessage = """
                为演员{actor}生成电影作品年表，带有作品时间。
                {format}
                """;
        log.info("output format:{}", outputParser.getFormat());
        PromptTemplate promptTemplate = new PromptTemplate(
                userMessage, Map.of("actor", actor, "format", outputParser.getFormat()));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();

        ActorsFilms actorsFilms = outputParser.parse(generation.getOutput().getContent());
        return JacksonUtil.ObjectToJson(actorsFilms);
    }
}
