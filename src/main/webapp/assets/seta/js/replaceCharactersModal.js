/* 
 * Function to replace characters
 */

function replaceCharactersModal(word) {
    word = word.replace("&", "%26");
    word = word.replace("?", "%3F");
    word = word.replace("%", "%25");
    word = word.replace("<", "%3C");
    word = word.replace(">", "%3E");
    word = word.replace("=", "%3D");
    word = word.replace("\"", "%22");
    word = word.replace("#", "%23");
    word = word.replace("$", "%24");
    word = word.replace("\'", "%27");
    word = word.replace("(", "%28");
    word = word.replace(")", "%29");
    word = word.replace("*", "%2A");
    word = word.replace("+", "%2B");
    word = word.replace(",", "%2C");
    word = word.replace("-", "%2D");
    word = word.replace(".", "%2E");
    word = word.replace("/", "%2F");
    word = word.replace("[", "%5B");
    word = word.replace("]", "%5D");
    word = word.replace("\\", "%5C");
    word = word.replace("^", "%5E");
    word = word.replace("_", "%5F");
    word = word.replace("`", "%60");
    word = word.replace("|", "%7C");
    word = word.replace("€", "%80");
    word = word.replace("½", "%BD");
    word = word.replace("~", "%7E");
    word = word.replace("{", "%7B");
    word = word.replace("}", "%7D");
    word = word.replace("‰", "%89");
    word = word.replace("£", "%C2%A3");
    word = word.replace("§", "%C2%A7");
    word = word.replace("°", "%C2%B0");
    word = word.replace("ç", "%C3%A7");
    word = word.replace("é", "%E9");
	
    return word;
}

