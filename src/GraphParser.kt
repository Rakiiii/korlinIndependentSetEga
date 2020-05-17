import Builders.ParserBuilding
import Parser.Parser
import java.io.File

class GraphParser : Parser,ParserBuilding
{
    override fun ParseItemsFromFile(path: String): MutableList<Any> {
        val graph = Graph()

        val reader = File(path).bufferedReader()
        val fstring = reader.readLine()

        val amV = fstring.substringBefore(" ").toInt()
        for (i in 0 until amV)
        {
            val stredg = reader.readLine()
            graph.edges.add(i,parseMutableListFromString(stredg))
        }
        return mutableListOf<Any>(graph as Any)
    }

    override fun Build(): Parser {
        return GraphParser()
    }

    fun parseMutableListFromString(_str : String):MutableList<Int>
    {
        var str = _str
        val result = mutableListOf<Int>()
        while ( str.contains(" "))
        {
            val edg = str.substringBefore(" ").toInt()
            result.add(edg)
            str = str.substringAfter(" ")
        }
        try {
            val lastRes = str.toInt()
            result.add(lastRes)
            result.sort()
            return result
        }catch (e : Exception)
        {
            result.sort()
            return result
        }
    }
}