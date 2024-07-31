//leetcode submit region begin(Prohibit modification and deletion)

// labuladong题解
// https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/qian-zhui--46e56/

// 力扣官方题解，bfs
// https://leetcode.cn/problems/implement-trie-prefix-tree/solutions/717239/shi-xian-trie-qian-zhui-shu-by-leetcode-ti500/?envType=study-plan-v2&envId=top-100-liked

class Trie {

    /** 方法一：最简单直接的思路实现
     *      在搜索匹配子节点的时候，
     *          问题一：用for循环来进行搜索，每次都要浪费51个不必要的循环
     *          问题二：每个父节点中，子结点val值和父节点children中存储的边值其实是一样的
     *      时间优化：（问题一）
     *          直接用 children[targetChar - 'a'] != null来判断即可，减少时间复杂度
     *      空间优化：（问题二）
     *          删除结点中val变量，减少空间复杂度
     */

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

class Trie {

    /** 方法二：时间、空间优化
     *      方法一在搜索匹配子节点的时候，
     *          问题一：用for循环来进行搜索，每次都要浪费51个不必要的循环
     *          问题二：每个父节点中，子结点val值和父节点children中存储的边值其实是一样的
     *      时间优化：（问题一）
     *          直接用 children[targetChar - 'a'] != null来判断即可，减少时间复杂度
     *      空间优化：（问题二）
     *          删除结点中val变量，减少空间复杂度
     */

    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/qian-zhui--46e56/

    class TrieNode {
        boolean isWord;
        TrieNode[] children = new TrieNode[52]; // 大小写的英文字母加起来最多52个字母
        TrieNode(boolean isWord) {
            this.isWord = isWord;
        };
    }

    TrieNode dummy;

    boolean isPrefix;  // 搜索单词 和 匹配前缀用的同一个dfs，只有一行代码不同，用这个全局变量进行判断

    public Trie() {
        // 初始化虚拟根节点
        dummy = new TrieNode(false);
    }

    public void insert(String word) {
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历，依次将前缀插入
        insert_dfs(dummy, chars, 0);
    }

    public void insert_dfs(TrieNode fatherRoot, char[] chars, int sonIndex) {
        // base case
        TrieNode sonNode = fatherRoot.children[chars[sonIndex]-'a'];
        // 如果这是最后一个需要插入的字符
        if (sonIndex == chars.length-1) {
            if (sonNode != null) {
                sonNode.isWord = true;
            } else {
                /** 注意：这里不能直接使用sonNode，因为此时sonNode==null，没有地址引用的传递 */
                sonNode = new TrieNode(true);
                fatherRoot.children[chars[sonIndex]-'a'] = sonNode;
            }
            return;
        }

        // 如果这不是最后一个需要插入的字符，dfs遍历
        /** 用 索引定位 来替代 for循环，优化时间 */
        if (sonNode != null) {
            // 将sonNode作为新父根，继续dfs匹配
            insert_dfs(sonNode, chars, sonIndex+1);
        } else { // 如果sonNode==null，也就是说父根下面没有这个可匹配的字符
            // 新建子节点，然后再dfs匹配
            sonNode = new TrieNode(false);
            fatherRoot.children[chars[sonIndex]-'a'] = sonNode;
            insert_dfs(sonNode, chars, sonIndex+1);
        }

    }

    public boolean search(String word) {
        isPrefix = false;
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历检查
        /** 搜索的逻辑：root的边中有没有sonChar */
        return search_dfs(dummy, chars, 0);
    }

    public boolean search_dfs(TrieNode fatherRoot, char[] chars, int sonIndex) {
        TrieNode sonNode = fatherRoot.children[chars[sonIndex]-'a'];
        // base case
        if (sonIndex == chars.length-1) {
            if (isPrefix) {
                return sonNode != null;
            } else {
                return sonNode != null && sonNode.isWord;
            }
        }

        // dfs遍历，判断root的边中，有没有sonChar
        // 如果没有这条字母边，说明匹配失败了
        if (sonNode == null) return false;
        // 如果有这条字母前缀边，继续向下搜索
        boolean match = search_dfs(sonNode, chars, sonIndex+1); // 返回后面的字母是否都能被匹配
        return match;
    }

    public boolean startsWith(String prefix) {
        isPrefix = true;
        char[] chars = prefix.toCharArray();
        // 从虚拟根结点开始，dfs遍历检查
        return search_dfs(dummy, chars, 0);
    }
}

class Trie {

    /** 方法三：继续优化空间复杂度
     *      前两种方法孩子节点的存储方式为数组形式，
     *      也就是说即使root下没有填满52个字母前缀，也要创建对应的地址空间，造成浪费
     *  空间优化思路：
     *      改用 哈希map 或者 哈希set 存储
     *      这样，每次就不需要创建对象时就初始化52个数组空间大小
     */

    class TrieNode {
        boolean isWord;
        Map<Character, TrieNode> children = new HashMap<>(); // 字符->节点对象
        TrieNode(boolean isWord) {
            this.isWord = isWord;
        };
    }

    TrieNode dummy;

    boolean isPrefix;  // 搜索单词 和 匹配前缀用的同一个dfs，只有一行代码不同，用这个全局变量进行判断

    public Trie() {
        // 初始化虚拟根节点
        dummy = new TrieNode(false);
    }

    public void insert(String word) {
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历，依次将前缀插入
        insert_dfs(dummy, chars, 0);
    }

    // todo 这里也可以改成bfs插入，遍历chars数组
    public void insert_dfs(TrieNode fatherRoot, char[] chars, int sonIndex) {
        // base case
        TrieNode sonNode = fatherRoot.children.get(chars[sonIndex]);
        // 如果这是最后一个需要插入的字符
        if (sonIndex == chars.length-1) {
            if (sonNode != null) {
                sonNode.isWord = true;
            } else {
                /** 注意：这里不能直接使用sonNode，因为此时sonNode==null，没有地址引用的传递 */
                sonNode = new TrieNode(true);
                fatherRoot.children.put(chars[sonIndex], sonNode);
            }
            return;
        }

        // 如果这不是最后一个需要插入的字符，dfs遍历
        /** 用 索引定位 来替代 for循环，优化时间 */
        if (sonNode != null) {
            // 将sonNode作为新父根，继续dfs匹配
            insert_dfs(sonNode, chars, sonIndex+1);
        } else { // 如果sonNode==null，也就是说父根下面没有这个可匹配的字符
            // 新建子节点，然后再dfs匹配
            sonNode = new TrieNode(false);
            fatherRoot.children.put(chars[sonIndex], sonNode);
            insert_dfs(sonNode, chars, sonIndex+1);
        }

    }

    public boolean search(String word) {
        isPrefix = false;
        char[] chars = word.toCharArray();
        // 从虚拟根结点开始，dfs遍历检查
        /** 搜索的逻辑：root的边中有没有sonChar */
        return search_dfs(dummy, chars, 0);
    }

    // 这里也可以改成bfs搜索，遍历chars数组
    public boolean search_dfs(TrieNode fatherRoot, char[] chars, int sonIndex) {
        TrieNode sonNode = fatherRoot.children.get(chars[sonIndex]);
        // base case
        if (sonIndex == chars.length-1) {
            if (isPrefix) {
                return sonNode != null;
            } else {
                return sonNode != null && sonNode.isWord;
            }
        }

        // dfs遍历，判断root的边中，有没有sonChar
        // 如果没有这条字母边，说明匹配失败了
        if (sonNode == null) return false;
        // 如果有这条字母前缀边，继续向下搜索
        boolean match = search_dfs(sonNode, chars, sonIndex+1); // 返回后面的字母是否都能被匹配
        return match;
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
