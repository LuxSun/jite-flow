package com.jite.flow.constant;

import com.jite.flow.engine.JobNode;

/**
 * @author Lux Sun
 * @date 2021/10/21
 */
public class Const {

    public static class Node {

        public static class DispatchRate {
            public static Boolean SYNC = false;
            public static Boolean ASYNC = true;
        }
    }

    public static class JobNode {

        public static class Call {
            public static Boolean IS_CALLED = true;
            public static Boolean NON_CALLED = false;
        }
    }

    public static class JobLine {

        public static class Mark {
            public static String FROM_TO = "@";
        }
    }

    public static class AbstractModule {

        public static class Key {
            public static String ID = "id";
        }
    }

    public static class Graph {

        public static class NodeLine {
            public static Boolean IS_NODE = true;
            public static Boolean NON_NODE = false;
            public static Boolean IS_LINE = true;
            public static Boolean NON_LINE = false;
        }
    }
}
