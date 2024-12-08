public record Coordinates(int x, int y) {
    public Coordinates add(TranslationVector tv)
    {
        return new Coordinates(x + tv.tx(), y + tv.ty());
    }

    public Coordinates copy()
    {
        return new Coordinates(this.x, this.y);
    }
}
