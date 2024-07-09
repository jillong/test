package com.example.springai.controller;


import com.example.springai.utils.JacksonUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jillong
 * @date 2024/6/7
 */

@Log4j2
@RestController
@RequestMapping("/ai/rag")
public class RAGController {

    @Resource
    private VectorStore vectorStore;

    @Resource
    private OpenAiChatClient chatClient;

    /**
     * RAG 模型创建
     *
     * @return 创建结果
     */
    @GetMapping("/rag/create")
    public String ragCreate() {
        // 1. 提取文本内容
        String filePath = "classpath:resume.txt";
        TextReader textReader = new TextReader(filePath);
        textReader.getCustomMetadata().put("filePath", filePath);
        List<Document> documents = textReader.get();
        log.info("documents before split:{}", documents);

        // 2. 文本切分为段落
        TextSplitter splitter = new TokenTextSplitter();
        documents = splitter.apply(documents);
        log.info("documents after split:{}", documents);

        // 3. 段落写入向量数据库
        vectorStore.add(documents);
        return JacksonUtil.ObjectToJson(documents);
    }

    /**
     * 根据RAG回答问题
     *
     * @param query
     * @return
     */

    @GetMapping("/agent")
    public String rag(@RequestParam(value = "query") String query) {
        // 首先检索挂载信息
        List<Document> documents = vectorStore.similaritySearch(query);
        // 提取最相关的信息
        String info = "";
        if (documents.size() > 0) {
            info = documents.get(0).getContent();
        }

        // 构造系统prompt
        String systemPrompt = """
                角色与目标：你是一个招聘助手，会针对用户的问题，结合候选人经历，岗位匹配度等专业知识，给用户提供指导。
                指导原则：你需要确保给出的建议合理且科学，不会对候选人的表现有侮辱言论。
                限制：在提供建议时，需要强调在个性化建议方面用户仍然需要线下寻求专业咨询。
                澄清：在与用户交互的过程中，你需要明确回答用户关于招聘方面的问题，对于非招聘相关的问题，你的回应是“我只是一个招聘助手，不能回答这个问题噢”。
                个性化：在回答时，你需要以专业、可靠的语气回答，偶尔也可以带些风趣和幽默，调节氛围。
                给你提供一些数据参考，并且给你调用岗位投递检索工具
                请你跟进数据参考与工具返回结果回复用户的请求。
                """;

        // 构造用户prompt
        String userPrompt = """
                给你提供一些数据参考: {info}，请回答我的问题：{query}
                请你跟进数据参考与工具返回结果回复用户的请求。
                """;

        // 构造提示词
        Message systemMessage = new SystemMessage(systemPrompt);
        PromptTemplate promptTemplate = new PromptTemplate(userPrompt);
        Message userMessage = promptTemplate.createMessage(Map.of("info", info, "query", query));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage),
                OpenAiChatOptions.builder().withFunctions(Set.of("recruitPosition")).build());

        // 调用大模型回答问题
        List<Generation> response = chatClient.call(prompt).getResults();
        StringBuilder result = new StringBuilder();
        for (Generation generation : response) {
            String content = generation.getOutput().getContent();
            result.append(content);
        }
        return JacksonUtil.ObjectToJson(result.toString());
    }
}
