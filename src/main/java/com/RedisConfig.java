package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.goods.model.GoodsVO;
import com.used.model.UsedVO;

@Configuration
public class RedisConfig {

	// 配置 Redis db5
	@Bean("redisConnectionFactoryDb5")
	public RedisConnectionFactory redisConnectionFactoryDb5() {
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		factory.setDatabase(5); // 指定 Redis 数据库编号为 5
		factory.afterPropertiesSet();
		return factory;
	}

	// 配置 Redis db6
	@Bean("redisConnectionFactoryDb6")
	public RedisConnectionFactory redisConnectionFactoryDb6() {
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		factory.setDatabase(6); // 指定 Redis 数据库编号为 6
		factory.afterPropertiesSet();
		return factory;
	}

	// 配置 Redis db7
	@Bean("redisConnectionFactoryDb7")
	public RedisConnectionFactory redisConnectionFactoryDb7() {
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		factory.setDatabase(7); // 指定 Redis 数据库编号为 7
		factory.afterPropertiesSet();
		return factory;
	}

	// 配置 Redis db8
	@Bean("redisConnectionFactoryDb8")
	@Primary
	public RedisConnectionFactory redisConnectionFactoryDb8() {
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		factory.setDatabase(8); // 指定 Redis 数据库编号为 8
		factory.afterPropertiesSet();
		return factory;
	}

	// 配置 Redis db10
	@Bean("redisConnectionFactoryDb10")
	public RedisConnectionFactory redisConnectionFactoryDb10() {
		LettuceConnectionFactory factory = new LettuceConnectionFactory();
		factory.setDatabase(10); // 指定 Redis 数据库编号为 10
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(
			@Qualifier("redisConnectionFactoryDb5") RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean(name = "redisTemplateDb8")
	public RedisTemplate<String, Object> redisTemplateDb8(
			@Qualifier("redisConnectionFactoryDb8") RedisConnectionFactory redisConnectionFactoryDb8) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactoryDb8);

		// 设置key序列化器为StringRedisSerializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);

		// 设置value序列化器为Jackson2JsonRedisSerializer
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	// 配置 RedisTemplate for db10 (String, String)
	@Bean(name = "redisTemplateDb10")
	public RedisTemplate<String, String> redisTemplateDb10(
			@Qualifier("redisConnectionFactoryDb10") RedisConnectionFactory redisConnectionFactoryDb10) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactoryDb10);

		// 设置 key 和 value 的序列化器为 StringRedisSerializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(stringRedisSerializer);

		template.afterPropertiesSet();
		return template;
	}

	@Bean(name = "redisTemplateDb7")
	public RedisTemplate<String, List<GoodsVO>> redisTemplate7(
			@Qualifier("redisConnectionFactoryDb7") RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, List<GoodsVO>> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		// 设置key序列化器为StringRedisSerializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);

		// 使用 Jackson2JsonRedisSerializer<Object>
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.WRAPPER_ARRAY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	@Bean(name = "redisTemplateDb6")
	public RedisTemplate<String, List<UsedVO>> redisTemplate6(
			@Qualifier("redisConnectionFactoryDb6") RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, List<UsedVO>> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		// 设置key序列化器为StringRedisSerializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);

		// 使用 Jackson2JsonRedisSerializer<Object>
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.WRAPPER_ARRAY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

}