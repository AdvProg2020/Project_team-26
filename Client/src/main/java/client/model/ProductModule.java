package client.model;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ProductModule extends SimpleModule {

    private static final String NAME = "CustomIntervalModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public ProductModule() {
        super(NAME, VERSION_UTIL.version());
        addSerializer(Product.class, new ProductSerializer());
    }

}
