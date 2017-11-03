package me.yamakaja.runtimetransformer;

import java.io.File;

public class TransformerUtilsAccess {

	public static void attachAgent(File agentFile, Class<?>[] transformers) {
		TransformerUtils.attachAgent(agentFile, transformers);
	}
	
	public static File saveAgentJar() {
		return TransformerUtils.saveAgentJar();
	}
	
}
