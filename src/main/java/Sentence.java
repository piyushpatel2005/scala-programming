public class Sentence {
    private String text;
    private int wordCount;

    public Sentence() {
        this.text = "";
        this.wordCount = 0;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void add(String word) {
        this.setText(this.getText() + " " + word.trim());
        this.setWordCount(this.getText().trim().length());
        return;
    }

    public String toString() {
        return this.getText();
    }
}
