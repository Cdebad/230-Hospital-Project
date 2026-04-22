public interface Identity {
    public boolean match( Identity other);
    public boolean isLessThan( Identity other);
}