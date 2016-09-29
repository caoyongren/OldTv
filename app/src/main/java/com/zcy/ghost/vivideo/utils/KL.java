package com.zcy.ghost.vivideo.utils;

/**
 * 继承自 L 的 Log 输出类，提供日志统一管控的功能。
 *
 * 防止 写Log的时候 引用包错误，因此通过该类
 *
 * 该类和 L类 实现相同功能，都可以处理日志。
 *
 * 1.多彩格式的日志，提供关键信息；
 *
 * 2.和slf4j  相同格式，统一管理；
 *
 * 3.抽离简化日志功能。
 *
 * Created by CP on 15/8/6.
 */
public class KL extends L {
}
