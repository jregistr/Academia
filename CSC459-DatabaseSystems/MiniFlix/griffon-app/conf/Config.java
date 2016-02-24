import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static java.util.Arrays.asList;
import static griffon.util.CollectionUtils.map;

public class Config extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
            .e("application", map()
                .e("title", "mini-flix")
                .e("startupGroups", asList("miniFlix"))
                .e("autoShutdown", true)
            )
            .e("mvcGroups", map()
                .e("miniFlix", map()
                    .e("model", "com.jeff.miniflix.MiniFlixModel")
                    .e("view", "com.jeff.miniflix.MiniFlixView")
                    .e("controller", "com.jeff.miniflix.MiniFlixController")
                )
            );
    }
}