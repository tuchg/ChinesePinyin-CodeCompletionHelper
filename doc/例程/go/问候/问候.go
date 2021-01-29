package 问候

import "fmt"

func W你好(名字 string) string {
    消息 := fmt.Sprintf("早, %v. 请进", 名字)
    return 消息
}