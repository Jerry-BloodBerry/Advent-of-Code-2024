public record TranslationVector(int tx, int ty) {
    public static TranslationVector FromCoordinates (Coordinates c1, Coordinates c2) {
        return new TranslationVector(c1.x() - c2.x(), c1.y() - c2.y());
    }
    public static TranslationVector Opposite(TranslationVector other)
    {
        return new TranslationVector(-other.tx, -other.ty);
    }
}
