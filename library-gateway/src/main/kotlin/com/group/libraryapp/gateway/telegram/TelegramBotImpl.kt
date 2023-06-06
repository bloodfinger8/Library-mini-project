package com.group.libraryapp.gateway.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Component

@Component
class TelegramBotImpl : Notifier {
    override fun registered(bookName: String) {
        val message = "관리자에 의해 $bookName 도서가 추가되었습니다."
        val bot = TelegramBot("6224839599:AAG8_FXjSwPOvpO_xVJuwGVYJSaIFF3nv-E")
        val request = SendMessage("-916688325", message)
        bot.execute(request)
    }

    override fun loaned(userName: String, bookName: String) {
        val message = "$userName 님이 $bookName 을 대여했습니다."
        val bot = TelegramBot("6224839599:AAG8_FXjSwPOvpO_xVJuwGVYJSaIFF3nv-E")
        val request = SendMessage("-916688325", message)
        bot.execute(request)
    }

    override fun returned(userName: String, bookName: String) {
        val message = "$userName 님이 $bookName 을 반납했습니다."
        val bot = TelegramBot("6224839599:AAG8_FXjSwPOvpO_xVJuwGVYJSaIFF3nv-E")
        val request = SendMessage("-916688325", message)
        bot.execute(request)
    }
}
