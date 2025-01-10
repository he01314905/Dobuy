package com.chat.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.model.ChatMessage;
import com.chat.model.ChatService;
import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@Controller
@RequestMapping("/C") // 添加前缀，确保路径唯一
public class ChatController {

	@Autowired
	ChatService chatSvc;

	@Autowired
	@Qualifier("stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	MemberService memberSvc;

//	接收消息并存储到 Redis，同时广播消息给所有订阅者

	@MessageMapping("/chat") // 客户端发送消息到 /app/chat 时，这个方法会被调用
	@SendTo("/topic/messages") // 方法返回的消息会广播到 /topic/messages，所有订阅该频道的客户端会收到
	public ChatMessage sendMessage(ChatMessage message) {
		// 获取 Redis 中发送者的 Key，例如：chat:A
		String senderKey = "chat:" + message.getSender(); // 例如 chat:A
		String receiverKey = "chat:" + message.getReceiver(); // 例如 chat:B

		// 将 ChatMessage 对象序列化为 JSON 字符串
		Gson gson = new Gson();
		String messageJson = gson.toJson(message);

		// 将消息存储到 Redis 的 List 中，右侧追加
		stringRedisTemplate.opsForList().rightPush(senderKey, messageJson); // 存储到发送者的记录
		stringRedisTemplate.opsForList().rightPush(receiverKey, messageJson); // 存储到接收者的记录

		// 返回消息，这个消息会被广播到 /topic/messages
		return message;
	}

	@MessageMapping("/sendMessage")
	public void sendMessage01(ChatMessage message, Principal principal) {
		// 从 Principal 中获取用户名
		// 获取 Redis 中发送者的 Key，例如：chat:A
		String senderKey = "chat:" + message.getSender(); // 例如 chat:A
		String receiverKey = "chat:" + message.getReceiver(); // 例如 chat:B
		String username = principal.getName();

		System.out.println("Current user: " + username);
		System.out.println("Receiver: " + message.getReceiver());

		// 将 ChatMessage 对象序列化为 JSON 字符串
		Gson gson = new Gson();
		String messageJson = gson.toJson(message);

		// 将消息存储到 Redis 的 List 中，右侧追加
		stringRedisTemplate.opsForList().rightPush(senderKey, messageJson); // 存储到发送者的记录
		stringRedisTemplate.opsForList().rightPush(receiverKey, messageJson); // 存储到接收者的记录
		// 设置发送者为当前用户
		message.setSender(username);
		chatSvc.sendToUser(message.getReceiver(), message);
		chatSvc.sendToUser(message.getSender(), message);
	}

//	获取指定发送者和接收者的聊天记录
//	@GetMapping("/chat/history")
//	@ResponseBody
//	public List<ChatMessage> getChatHistory(@RequestParam String sender, @RequestParam String receiver) {
//
//		// 获取当前用户的聊天记录(所有)
//		List<String> senderMessages = stringRedisTemplate.opsForList().range("chat:" + sender, 0, -1);
//
//		// 獲取指定對象的聊天紀錄
//		List<ChatMessage> result = new ArrayList<>();
//
//		// 筛选与指定接收者的聊天记录
//		for (String message : senderMessages) {
//			ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
//			if (chatMessage.getReceiver().equals(receiver) || chatMessage.getSender().equals(receiver)) {
//				result.add(chatMessage);
//			}
//		}
//		return result;
//	}

	@PostMapping("/startChat")
	public String getChatroom(@RequestBody Map<String, String> body, Model model, HttpSession session) {
		String sender = body.get("sender");
		String receiver = body.get("receiver");
		String username = (String) session.getAttribute("username");

		System.out.println("Current user: " + username);
		// 获取当前用户的聊天记录
		List<String> senderMessages = stringRedisTemplate.opsForList().range("chat:" + sender, 0, -1);

		// 筛选与指定接收者的聊天记录
		List<ChatMessage> result = new ArrayList<>();
		for (String message : senderMessages) {
			ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
			if (chatMessage.getReceiver().equals(receiver) || chatMessage.getSender().equals(receiver)) {
				result.add(chatMessage);
			}
		}

		// 将发送者、接收者和聊天记录添加到模型中
		model.addAttribute("username", username);
		model.addAttribute("sender", sender);
		model.addAttribute("receiver", receiver);
		model.addAttribute("messages", result);

		// 返回聊天室页面
		return "front-end/chat/chatRoom :: chat-content";
	}

	@PostMapping("/getChatList")
	public String getChatList(@RequestBody Map<String, String> body, Model model) {
		String receiver = body.get("receiver");

		// 获取当前用户的聊天记录
		List<String> senderMessages = stringRedisTemplate.opsForList().range("chat:" + receiver, 0, -1);
		Collections.reverse(senderMessages);// 順序反轉 新的在前面
		// 筛选与指定接收者的聊天记录
		Set<String> result = new LinkedHashSet<>();
		for (String message : senderMessages) {
			ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
			if (chatMessage.getReceiver().equals(receiver)) { // 只找receiver(因為要返回的是顧客的帳號，不包含自己)
				result.add(chatMessage.getSender());
			}
		}
		// 将发送者、接收者和聊天记录添加到模型中
		model.addAttribute("coustomer", result);

		// 返回聊天室页面
		return "front-end/chat/chatList :: chat-list";
	}

	@GetMapping("/chatHome")
	public String chatHome(Model model) {

		List<MemberVO> memberList = memberSvc.getAll();

		model.addAttribute("memberList", memberList);

		return "front-end/chat/chatHome";
	}
}
