package feign.example.github;

import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;

public final class ManualFeignClientConstructor {

    private ManualFeignClientConstructor() {
    }

    public static <T> T connect(Class<T> apiType, String url) {
        Decoder decoder = new GsonDecoder();
        return Feign.builder()
                .decoder(decoder)
                .errorDecoder(new GitHubErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(apiType, url);
    }
}
