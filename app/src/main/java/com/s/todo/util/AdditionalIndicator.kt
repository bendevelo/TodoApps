package com.s.todo.util

import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.Date

class AdditionalIndicator(
    override val color: Int,
    override val date: CalendarDate

) : CalendarView.DateIndicator