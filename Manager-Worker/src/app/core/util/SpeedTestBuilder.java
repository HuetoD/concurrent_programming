package app.core.util;

public class SpeedTestBuilder implements TestCreator<SpeedTestBuilder> {

    protected final long init;

    protected SpeedTestBuilder() {
        init = System.currentTimeMillis();
    }

    @Override
    public SpeedTestBuilder print(String info) {
        System.out.print(info);
        return this;
    }

    @Override
    public SpeedTestBuilder execute(SpeedAction action) {
        action.callback();
        return this;
    }

    @Override
    public SpeedTest end() {
        return new SpeedTest(init, System.currentTimeMillis());
    }
}
