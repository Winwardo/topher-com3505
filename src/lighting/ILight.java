package lighting;

public interface ILight {
    public void apply();

    public void enable(boolean enabled);

    public void hide(boolean hidden);
}
