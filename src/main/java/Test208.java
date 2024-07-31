import java.util.LinkedList;

public class Test208 {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));
        System.out.println(trie.search("app"));
        System.out.println(trie.startsWith("app"));
        trie.insert("app");
        System.out.println(trie.search("app"));
    }

}



//leetcode submit region begin(Prohibit modification and deletion)
class Trie {

    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/qian-zhui--46e56/

    class TrieNode {
        int val;
        boolean isWord;
        TrieNode[] children = new TrieNode[52]; // 大小写的英文字母加起来最多52个字母
        TrieNode(int val, boolean isWord) {
            this.val = val;
            this.isWord = isWord;
        };
    }

    TrieNode dummy;

    boolean isPrefix;  // 搜索单词 和 匹配前缀用的同一个dfs，只有一行代码不同，用这个全局变量进行判断

    public Trie() {
        // 初始化虚拟根节点
        dummy = new TrieNode(-1, false);
    }

    public void insert(String word) {
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历，依次将前缀插入
        insert_dfs(dummy, chars, 0);
    }

    public void insert_dfs(TrieNode root, char[] chars, int targetIndex) {
        // base case写在下面了

        // dfs遍历root的子节点
        boolean flag = false;
        for (TrieNode node : root.children) {
            // 1.如果有这个字母的子节点，直接dfs进入
            if (node != null && node.val == chars[targetIndex]-'a') {
                // 如果这是需要匹配的最后一个字母了，设置这里为单词并直接返回
                if (targetIndex == chars.length-1) {
                    node.isWord = true;
                    return;
                }
                // 如果不是最后一个字母，继续匹配后面
                insert_dfs(node, chars, targetIndex+1);
                flag = true;
                break;
            }
        }
        // 2.否则，创建这个字母子节点，再dfs进入
        if (!flag) {
            if (targetIndex == chars.length-1) {
                // 如果是最后一个字母，此时存储是单词
                TrieNode node = new TrieNode(chars[targetIndex] - 'a', true);
                // 加入到root的孩子数组
                root.children[chars[targetIndex] - 'a'] = node;
                return;
            } else {
                TrieNode node = new TrieNode(chars[targetIndex] - 'a', false);
                // 加入到root的孩子数组
                root.children[chars[targetIndex] - 'a'] = node;
                insert_dfs(node, chars, targetIndex+1);
            }
        }
    }

    public boolean search(String word) {
        isPrefix = false;
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历检查
        /** 搜索的逻辑：root的孩子结点中有没有targetIndex */
        return search_dfs(dummy, chars, 0);
    }

    public boolean search_dfs(TrieNode root, char[] chars, int targetIndex) {
        // base case
        // 遍历到最后
        if (targetIndex == chars.length-1) {
            if (isPrefix) {
                return root.children[chars[targetIndex]-'a'] != null;
            } else {
                return root.children[chars[targetIndex]-'a'] != null && root.children[chars[targetIndex]-'a'].isWord;
            }
        }

        // dfs遍历，判断root的孩子中，有没有和chars[targetIndex]匹配的
        for (TrieNode node : root.children) {
            // 子节点前序
            // 如果有这个字母的前缀结点，继续向下搜索
            if (node != null && node.val == chars[targetIndex]-'a') {
                boolean match = search_dfs(node, chars, targetIndex+1);  // 返回后面的字母是否都能被匹配
                return match;
            }
        }

        // 执行到这里，说明当前字母没有成功匹配的，说明 前缀树中 没有这个单词
        return false;
    }

    public boolean startsWith(String prefix) {
        isPrefix = true;
        char[] chars = prefix.toCharArray();
        // 从虚拟根结点开始，dfs遍历检查
        return search_dfs(dummy, chars, 0);
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
//leetcode submit region end(Prohibit modification and deletion)

