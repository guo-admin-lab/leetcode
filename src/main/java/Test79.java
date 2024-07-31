public class Test79 {

    public static void main(String[] args) {

        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        Solution79 solution79 = new Solution79();
        boolean b = solution79.method1(board, word);
        System.out.println(b);
    }

}

// 比较慢
class Solution79 {

    public boolean exist(char[][] board, String word) {
        return method1(board, word);
    }

    // 方法一：回溯法dfs
    int maxRi;
    int maxCi;
    int rows;
    int cols;
    String targetWord;
    boolean[] visited;
    public boolean method1(char[][] board, String word) {

        // 1.初始化变量
        rows = board.length;
        cols = board[0].length;
        maxRi = rows-1;
        maxCi = cols-1;
        targetWord = word;
        visited = new boolean[rows * cols];

        // 矩阵中每个元素都当作起始点dfs遍历
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                boolean match = dfs(board, ri, ci, 0);
                if (match) return true;
            }
        }

        return false;
    }

    // 回溯dfs
    public boolean dfs(char[][] board, int ri, int ci, int wordCurIndex) {
        // base case
        if (wordCurIndex == targetWord.length()-1) {
            if (board[ri][ci] == targetWord.charAt(wordCurIndex)) return true;
            else return false;
        }

        // 前序
        if (board[ri][ci] != targetWord.charAt(wordCurIndex)) return false;
        // 这两行代码顺序不能变，因为要保证return false之前将visited变成false
        visited[ri * cols + ci] = true;

        // 四周遍历
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] direct : directions) {
            int nxtRi = ri + direct[0];
            int nxtCi = ci + direct[1];
            // 索引越界
            if (nxtRi < 0 || nxtRi > maxRi || nxtCi < 0 || nxtCi > maxCi) continue;
            // 已经访问过
            if (visited[nxtRi * cols + nxtCi]) continue;
            // 返回后面的字符是否能够匹配
            boolean match = dfs(board, nxtRi, nxtCi, wordCurIndex+1);
            if (match) return true;
            // 如果没有匹配成功，需要搜索下一个方向看是否能匹配成功，而不是直接返回
        }

        // 后序
        visited[ri * cols + ci] = false;

        // 执行到这里，说明下一个字母没有合适的匹配了
        return false;
    }

}

// 很快，不知道为什么，感觉逻辑都是一样的
// https://leetcode.cn/problems/word-search/solutions/2361646/79-dan-ci-sou-suo-hui-su-qing-xi-tu-jie-5yui2/?envType=study-plan-v2&envId=top-100-liked
class Solution79_1 {
    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if (dfs(board, words, i, j, 0)) return true;
            }
        }
        return false;
    }
    boolean dfs(char[][] board, char[] word, int i, int j, int k) {
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word[k]) return false;
        if (k == word.length - 1) return true;
        board[i][j] = '\0';
        boolean res = dfs(board, word, i + 1, j, k + 1) || dfs(board, word, i - 1, j, k + 1) ||
                dfs(board, word, i, j + 1, k + 1) || dfs(board, word, i , j - 1, k + 1);
        board[i][j] = word[k];
        return res;
    }
}
