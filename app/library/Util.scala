package library

import play.Play
import java.io.File

object Util {
  def getUnameFromSubdomain(subdomain: String) = subdomain.split('.')(0)

  def calcUniquifiedFilePath(originalPath: String) = {
    def makeNewFileCandidate(dirName: String, fileBaseName: String, fileExtName: String, prefixNum: Integer): String = {
      val temporaryPath = s"$dirName/$fileBaseName-$prefixNum.$fileExtName"

      (new File(temporaryPath)).exists match {
        case true => makeNewFileCandidate(dirName, fileBaseName, fileExtName, prefixNum + 1)
        case false => temporaryPath
      }
    }

    (new File(originalPath)).exists match {
      case true => {
        val dirName = originalPath.substring(0, originalPath.lastIndexOf('/'))
        val fileName = originalPath.substring(originalPath.lastIndexOf('/') + 1, originalPath.length)
        val fileBaseName = fileName.substring(0, fileName.lastIndexOf('.'))
        val fileExtName = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length)

        makeNewFileCandidate(dirName, fileBaseName, fileExtName, 1)
      }
      case false => originalPath
    }
  }

  def urlifyFilePath(originalPath: String) =
    originalPath.substring(Play.application.path.toString.length, originalPath.length).replace("/public/", "/assets/")
}
