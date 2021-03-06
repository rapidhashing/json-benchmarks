package net.rapidhashing.jsonbenchmarks;

import java.util.HashMap;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonBenchmark {
	private static final String TEST_INPUT = "{\"jsonrpc\": \"2.0\", \"method\": \"login\", \"params\": {\"username\": \"3495843958\", \"password\": \"hunter2\", \"user-agent\": \"not-a-botnet-0.1\"}, \"id\": 1}";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Map<String, Object> PARAMS = new HashMap<>();

	static {
		PARAMS.put("username", "3495843958");
		PARAMS.put("password", "hunter2");
		PARAMS.put("user-agent", "not-a-botnet-0.1");
	}

	@Benchmark
	public void benchmarkDecode(Blackhole bh) throws Exception {
		JsonRpcCall result = MAPPER.readValue(TEST_INPUT, JsonRpcCall.class);
		bh.consume(result);
	}

	@Benchmark
	public void benchmarkEncode(Blackhole bh) throws Exception {
		JsonRpcCall call = new JsonRpcCall("2.0", "login", 1, PARAMS);
		bh.consume(MAPPER.writeValueAsString(call));
	}
}
