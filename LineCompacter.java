import java.util.Vector;

class LineCompacter {

    private final Vector<String> lines;
    private int lineSize;
    private String longLine;

    public LineCompacter(int lineSize) {
        this.lineSize = lineSize;
        lines = new Vector<String>();
        longLine = null;
    }

    public LineCompacter(String longLine, int lineSize) {
        this.longLine = longLine;
        this.lineSize = lineSize;
        lines = new Vector<String>();
    }

    /*
    stroke to test:
    Puppy the hedgehog bite big frog to a leg with his super knife +4. Big frog looks very ill. Big frog was slain by Puppy the hedgehog! you got 8 level and 230 skill points. Your swim ability is now even higher!
     */

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public void setLongLine(String longLine) {
        this.longLine = longLine;
    }

    public void compact() {
        lines.clear();
        if (longLine == null) return;
        if (longLine.length() == 1) {
            lines.add(longLine);
            return;
        }
        int c = 0;
        int last = 0;
        while (c < longLine.length() - 1) {
            while (c < longLine.length() - 1 && c - last < lineSize) c++;
            char ch = longLine.charAt(c);
            while (c != longLine.length() - 1 && c > 0 && ch != ' ' && ch != '.' && ch != '!' && ch != '?') {
                c--;
                ch = longLine.charAt(c);
            }
            if (c == last) c = longLine.length() - 1;
            lines.add(longLine.substring(last, c + 1));
            last = c;
        }
    }

    public void show() {
        for (String line : lines) System.out.println(line);
    }

    public Vector<String> getLines() {
        return lines;
    }
}
