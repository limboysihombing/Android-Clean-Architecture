package com.app.habittracker.utils

import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun getCurrentDate() : String {
        val sdf = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentYear() : String {
        return Calendar.getInstance().get(Calendar.YEAR).toString()
    }

    fun getCurrentDay() : String {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getPreviousMonth() : String {
        return  (if (Calendar.getInstance().get(Calendar.MONTH) == 0) 12 else Calendar.getInstance().get(Calendar.MONTH)).toString().padStart(2, '0')
    }

    fun getCurrentMonth() : String {
        return (Calendar.getInstance().get(Calendar.MONTH) + 1).toString().padStart(2, '0')
    }

    fun getNextMonth() : String {
        return  (if ((Calendar.getInstance().get(Calendar.MONTH) + 2) > 12) 0 else (Calendar.getInstance().get(Calendar.MONTH) + 2)).toString().padStart(2, '0')
    }

    fun getCurrentTime() : String {
        val sdf = SimpleDateFormat(Constant.FORMAT_TIME_SORT, Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentDateTime() : String {
        val sdf = SimpleDateFormat(Constant.FORMAT_ISO_DATETIME, Locale.getDefault())
        return sdf.format(Date())
    }

    fun getStartDate() : String {
        val sdf = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        return sdf.format(calendar.time)
    }

    fun getEndDate() : String {
        val sdf = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return sdf.format(calendar.time)
    }

    fun diffDays(fromDate: String, toDate: String): Long {
        val fd = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.US).parse(fromDate)!!
        val td = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.US).parse(toDate)!!
        val fdTime = fd.time
        val tdTime = td.time
        val diff = tdTime - fdTime
        return diff / (24 * 60 * 60 * 1000)
    }

    fun getTimestamp(): String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(Calendar.getInstance().time)
    }

//    fun writeResponseBodyToDisk(filename: String, body: ResponseBody): File? {
//        try {
//            val docsFolder = File(Constant.PATH_LOCAL_DOC)
//            if (!docsFolder.exists()) docsFolder.mkdirs()
//
//            var inputStream: InputStream? = null
//            var outputStream: OutputStream? = null
//
//            val file = File(docsFolder.absolutePath + File.separator + filename + ".pdf")
//
//            try {
//                val fileReader = ByteArray(4096)
//
//                inputStream = body.byteStream()
//                outputStream = FileOutputStream(file)
//
//                while (true) {
//                    val read = inputStream.read(fileReader)
//                    if (read == -1) break
//                    outputStream.write(fileReader, 0, read)
//                }
//
//                outputStream.flush()
//                return file
//            } catch (ioEx: IOException) {
//                ioEx.printStackTrace()
//                return null
//            } finally {
//                inputStream?.close()
//                outputStream?.close()
//            }
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return null
//        }
//    }
}
