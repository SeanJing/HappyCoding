import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

/**
 * Created by xiaojing on 12/2/17.
 */
public class SeamCarver {
    private Picture pic = null;
    private double[][] energy = null;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture is null");
        this.pic = new Picture(picture);
        energy = new double[width()][height()];
        for (int row = 0; row < height(); row++)
            for (int col = 0; col < width(); col++)
                energy[col][row] = energy(col, row);
    }

    // current picture
    public Picture picture() {
        return this.pic;
    }

    // width of current picture
    public int width() {
        return this.pic.width();
    }

    // height of current picture
    public int height() {
        return this.pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) throw new IllegalArgumentException("index out of range");
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000;
        int dx = getDx(x, y);
        int dy = getDy(x, y);
        return Math.sqrt(dx + dy);
    }

    private int getDx(int x, int y) {
        Color prev = this.pic.get(x - 1, y);
        Color next = this.pic.get(x + 1, y);
        int dR = prev.getRed() - next.getRed();
        int dG = prev.getGreen() - next.getGreen();
        int dB = prev.getBlue() - next.getBlue();
        return dR * dR + dG * dG + dB * dB;
    }

    private int getDy(int x, int y) {
        Color prev = this.pic.get(x, y - 1);
        Color next = this.pic.get(x, y + 1);
        int dR = prev.getRed() - next.getRed();
        int dG = prev.getGreen() - next.getGreen();
        int dB = prev.getBlue() - next.getBlue();
        return dR * dR + dG * dG + dB * dB;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    private void transpose() {
        Picture transpose = new Picture(height(), width());
        double[][] transEnergy = new double[height()][width()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++) {
                transpose.set(j, i, this.pic.get(i, j));
                transEnergy[j][i] = energy[i][j];
            }
        this.pic = transpose;
        this.energy = transEnergy;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int width = width();
        int height = height();
        double[][] distTo = new double[width][height];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                distTo[col][row] = Double.MAX_VALUE;
        for (int i = 0; i < width; i++) distTo[i][0] = energy[i][0];

        int[][] edgeTo = new int[width][height];
        for (int i = 0; i < width; i++) edgeTo[i][0] = i;
        for (int row = 1; row < height; row++)
            for (int col = 0; col < width; col++) {
                for (int n = col - 1; n <= col + 1; n++) {
                    if (n < 0 || n >= width) continue;
                    if (distTo[col][row] > distTo[n][row - 1] + energy[col][row]) {
                        distTo[col][row] = distTo[n][row - 1] + energy[col][row];
                        edgeTo[col][row] = n;
                    }
                }
            }

        double min = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < width; i++)
            if (distTo[i][height - 1] < min) {
                minIndex = i;
                min = distTo[i][height - 1];
            }

        int[] seam = new int[height];
        seam[height - 1] = minIndex;
        for (int i = height - 1; i > 0; i--) {
            seam[i - 1] = edgeTo[seam[i]][i];
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("input is null");
        if (seam.length != width()) throw new IllegalArgumentException("input size is wrong");
        checkSeam(seam);
        Picture newPic = new Picture(width(), height() - 1);
        for (int i = 0; i < width(); i++) {
            int k = 0;
            for (int j = 0; j < height(); j++) {
                if (j != seam[i])
                    newPic.set(i, k++, this.pic.get(i, j));
            }
        }
        this.pic = newPic;
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energy[x][y] = energy(x, y);
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("input is null");
        if (seam.length != height()) throw new IllegalArgumentException("input size is wrong");
        checkSeam(seam);
        Picture newPic = new Picture(width() - 1, height());
        for (int i = 0; i < height(); i++) {
            int k = 0;
            for (int j = 0; j < width(); j++) {
                if (j != seam[i])
                    newPic.set(k++, i, this.pic.get(j, i));
            }
        }
        this.pic = newPic;
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energy[x][y] = energy(x, y);
            }
        }
    }

    private void checkSeam(int[] seam) {
        if (width() < 1 || height() < 1) {
            throw new IllegalArgumentException("Invalid picture");
        }
        if (seam.length < 1) {
            throw new IllegalArgumentException("Invalid seam");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("Invalid seam input");
            }
        }
    }

    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = sc.findVerticalSeam();
        sc.removeVerticalSeam(seam);
    }
}
