package org.peaceful.banana.git.util;

/**
 This file is part of Peaceful Banana.

 Peaceful Banana is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Peaceful Banana is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.
 */
public class WordProcessor {
    
    private String norwegianStopWords = "alle " +
            "andre " +
            "at " +
            "av " +
            "bare " +
            "begge " +
            "ble " +
            "bli " +
            "blir " +
            "blitt " +
            "bort " +
            "bra " +
            "bruke " +
            "både " +
            "da " +
            "de " +
            "deg " +
            "dem " +
            "den " +
            "denne " +
            "der " +
            "dere " +
            "deres " +
            "det " +
            "dette " +
            "din " +
            "disse " +
            "dit " +
            "ditt " +
            "du " +
            "eller " +
            "en " +
            "ene " +
            "eneste " +
            "enhver " +
            "enn " +
            "er " +
            "et " +
            "ett " +
            "etter " +
            "for " +
            "fordi " +
            "forsøke " +
            "fra " +
            "fram " +
            "før " +
            "først " +
            "få " +
            "gjorde " +
            "gjøre " +
            "god " +
            "gå " +
            "ha " +
            "hadde " +
            "han " +
            "hans " +
            "har " +
            "hennar " +
            "henne " +
            "hennes " +
            "her " +
            "hit " +
            "hun " +
            "hva " +
            "hvem " +
            "hver " +
            "hvilke " +
            "hvilken " +
            "hvis " +
            "hvor " +
            "hvordan " +
            "hvorfor " +
            "i " +
            "ikke " +
            "ingen " +
            "inn " +
            "innen " +
            "inni " +
            "ja " +
            "jeg " +
            "kan " +
            "kom " +
            "kun " +
            "kunne " +
            "lage " +
            "lang " +
            "lik " +
            "like " +
            "man " +
            "mange " +
            "med " +
            "meg " +
            "meget " +
            "mellom " +
            "men " +
            "mens " +
            "mer " +
            "mest " +
            "min " +
            "min " +
            "mitt " +
            "mot " +
            "mye " +
            "må " +
            "måte " +
            "ned " +
            "nei " +
            "noe " +
            "noen " +
            "ny " +
            "nå " +
            "når " +
            "og " +
            "også " +
            "om " +
            "opp " +
            "oss " +
            "over " +
            "på " +
            "rett " +
            "riktig " +
            "samme " +
            "seg " +
            "selv " +
            "si " +
            "siden " +
            "sin " +
            "sine " +
            "sist " +
            "sitt " +
            "sjøl " +
            "skal " +
            "skulle " +
            "slik " +
            "slutt " +
            "som " +
            "start " +
            "stille " +
            "så " +
            "sånn " +
            "tid " +
            "til " +
            "tilbake " +
            "under " +
            "ut " +
            "uten " +
            "var " +
            "ved " +
            "verdi " +
            "vi " +
            "vil " +
            "ville " +
            "vite " +
            "være " +
            "vært " +
            "vår " +
            "å " +
            "blei " +
            "båe " +
            "dei " +
            "deim " +
            "deira " +
            "deires " +
            "di " +
            "dykk " +
            "dykkar " +
            "då " +
            "eg " +
            "ein " +
            "eit " +
            "eitt " +
            "elles " +
            "hjå " +
            "ho " +
            "hoe " +
            "honom " +
            "hoss " +
            "hossen " +
            "ikkje " +
            "ingi " +
            "inkje " +
            "korleis " +
            "korso " +
            "kva " +
            "kvar " +
            "kvarhelst " +
            "kven " +
            "kvi " +
            "kvifor " +
            "me " +
            "medan " +
            "mi " +
            "mine " +
            "mykje " +
            "no " +
            "noka " +
            "noko " +
            "nokon " +
            "nokor " +
            "nokre " +
            "si " +
            "sia " +
            "sidan " +
            "so " +
            "somme " +
            "somt " +
            "um " +
            "upp " +
            "vart " +
            "varte " +
            "vere " +
            "verte " +
            "vore " +
            "vors " +
            "vort";
    private String englishStopWords = "a "+
            "a's "+
            "able "+
            "about "+
            "above "+
            "according "+
            "accordingly "+
            "across "+
            "actually "+
            "after "+
            "afterwards "+
            "again "+
            "against "+
            "ain't "+
            "all "+
            "allow "+
            "allows "+
            "almost "+
            "alone "+
            "along "+
            "already "+
            "also "+
            "although "+
            "always "+
            "am "+
            "among "+
            "amongst "+
            "an "+
            "and "+
            "another "+
            "any "+
            "anybody "+
            "anyhow "+
            "anyone "+
            "anything "+
            "anyway "+
            "anyways "+
            "anywhere "+
            "apart "+
            "appear "+
            "appreciate "+
            "appropriate "+
            "are "+
            "aren't "+
            "around "+
            "as "+
            "aside "+
            "ask "+
            "asking "+
            "associated "+
            "at "+
            "available "+
            "away "+
            "awfully "+
            "b "+
            "be "+
            "became "+
            "because "+
            "become "+
            "becomes "+
            "becoming "+
            "been "+
            "before "+
            "beforehand "+
            "behind "+
            "being "+
            "believe "+
            "below "+
            "beside "+
            "besides "+
            "best "+
            "better "+
            "between "+
            "beyond "+
            "both "+
            "brief "+
            "but "+
            "by "+
            "c "+
            "c'mon "+
            "c's "+
            "came "+
            "can "+
            "can't "+
            "cannot "+
            "cant "+
            "cause "+
            "causes "+
            "certain "+
            "certainly "+
            "changes "+
            "clearly "+
            "co "+
            "com "+
            "come "+
            "comes "+
            "concerning "+
            "consequently "+
            "consider "+
            "considering "+
            "contain "+
            "containing "+
            "contains "+
            "corresponding "+
            "could "+
            "couldn't "+
            "course "+
            "currently "+
            "d "+
            "definitely "+
            "described "+
            "despite "+
            "did "+
            "didn't "+
            "different "+
            "do "+
            "does "+
            "doesn't "+
            "doing "+
            "don't "+
            "done "+
            "down "+
            "downwards "+
            "during "+
            "e "+
            "each "+
            "edu "+
            "eg "+
            "eight "+
            "either "+
            "else "+
            "elsewhere "+
            "enough "+
            "entirely "+
            "especially "+
            "et "+
            "etc "+
            "even "+
            "ever "+
            "every "+
            "everybody "+
            "everyone "+
            "everything "+
            "everywhere "+
            "ex "+
            "exactly "+
            "example "+
            "except "+
            "f "+
            "far "+
            "few "+
            "fifth "+
            "first "+
            "five "+
            "followed "+
            "following "+
            "follows "+
            "for "+
            "former "+
            "formerly "+
            "forth "+
            "four "+
            "from "+
            "further "+
            "furthermore "+
            "g "+
            "get "+
            "gets "+
            "getting "+
            "given "+
            "gives "+
            "go "+
            "goes "+
            "going "+
            "gone "+
            "got "+
            "gotten "+
            "greetings "+
            "h "+
            "had "+
            "hadn't "+
            "happens "+
            "hardly "+
            "has "+
            "hasn't "+
            "have "+
            "haven't "+
            "having "+
            "he "+
            "he's "+
            "hello "+
            "help "+
            "hence "+
            "her "+
            "here "+
            "here's "+
            "hereafter "+
            "hereby "+
            "herein "+
            "hereupon "+
            "hers "+
            "herself "+
            "hi "+
            "him "+
            "himself "+
            "his "+
            "hither "+
            "hopefully "+
            "how "+
            "howbeit "+
            "however "+
            "i "+
            "i'd "+
            "i'll "+
            "i'm "+
            "i've "+
            "ie "+
            "if "+
            "ignored "+
            "immediate "+
            "in "+
            "inasmuch "+
            "inc "+
            "indeed "+
            "indicate "+
            "indicated "+
            "indicates "+
            "inner "+
            "insofar "+
            "instead "+
            "into "+
            "inward "+
            "is "+
            "isn't "+
            "it "+
            "it'd "+
            "it'll "+
            "it's "+
            "its "+
            "itself "+
            "j "+
            "just "+
            "k "+
            "keep "+
            "keeps "+
            "kept "+
            "know "+
            "knows "+
            "known "+
            "l "+
            "last "+
            "lately "+
            "later "+
            "latter "+
            "latterly "+
            "least "+
            "less "+
            "lest "+
            "let "+
            "let's "+
            "like "+
            "liked "+
            "likely "+
            "little "+
            "look "+
            "looking "+
            "looks "+
            "ltd "+
            "m "+
            "mainly "+
            "many "+
            "may "+
            "maybe "+
            "me "+
            "mean "+
            "meanwhile "+
            "merely "+
            "might "+
            "more "+
            "moreover "+
            "most "+
            "mostly "+
            "much "+
            "must "+
            "my "+
            "myself "+
            "n "+
            "name "+
            "namely "+
            "nd "+
            "near "+
            "nearly "+
            "necessary "+
            "need "+
            "needs "+
            "neither "+
            "never "+
            "nevertheless "+
            "new "+
            "next "+
            "nine "+
            "no "+
            "nobody "+
            "non "+
            "none "+
            "noone "+
            "nor "+
            "normally "+
            "not "+
            "nothing "+
            "novel "+
            "now "+
            "nowhere "+
            "o "+
            "obviously "+
            "of "+
            "off "+
            "often "+
            "oh "+
            "ok "+
            "okay "+
            "old "+
            "on "+
            "once "+
            "one "+
            "ones "+
            "only "+
            "onto "+
            "or "+
            "other "+
            "others "+
            "otherwise "+
            "ought "+
            "our "+
            "ours "+
            "ourselves "+
            "out "+
            "outside "+
            "over "+
            "overall "+
            "own "+
            "p "+
            "particular "+
            "particularly "+
            "per "+
            "perhaps "+
            "placed "+
            "please "+
            "plus "+
            "possible "+
            "presumably "+
            "probably "+
            "provides "+
            "q "+
            "que "+
            "quite "+
            "qv "+
            "r "+
            "rather "+
            "rd "+
            "re "+
            "really "+
            "reasonably "+
            "regarding "+
            "regardless "+
            "regards "+
            "relatively "+
            "respectively "+
            "right "+
            "s "+
            "said "+
            "same "+
            "saw "+
            "say "+
            "saying "+
            "says "+
            "second "+
            "secondly "+
            "see "+
            "seeing "+
            "seem "+
            "seemed "+
            "seeming "+
            "seems "+
            "seen "+
            "self "+
            "selves "+
            "sensible "+
            "sent "+
            "serious "+
            "seriously "+
            "seven "+
            "several "+
            "shall "+
            "she "+
            "should "+
            "shouldn't "+
            "since "+
            "six "+
            "so "+
            "some "+
            "somebody "+
            "somehow "+
            "someone "+
            "something "+
            "sometime "+
            "sometimes "+
            "somewhat "+
            "somewhere "+
            "soon "+
            "sorry "+
            "specified "+
            "specify "+
            "specifying "+
            "still "+
            "sub "+
            "such "+
            "sup "+
            "sure "+
            "t "+
            "t's "+
            "take "+
            "taken "+
            "tell "+
            "tends "+
            "th "+
            "than "+
            "thank "+
            "thanks "+
            "thanx "+
            "that "+
            "that's "+
            "thats "+
            "the "+
            "their "+
            "theirs "+
            "them "+
            "themselves "+
            "then "+
            "thence "+
            "there "+
            "there's "+
            "thereafter "+
            "thereby "+
            "therefore "+
            "therein "+
            "theres "+
            "thereupon "+
            "these "+
            "they "+
            "they'd "+
            "they'll "+
            "they're "+
            "they've "+
            "think "+
            "third "+
            "this "+
            "thorough "+
            "thoroughly "+
            "those "+
            "though "+
            "three "+
            "through "+
            "throughout "+
            "thru "+
            "thus "+
            "to "+
            "together "+
            "too "+
            "took "+
            "toward "+
            "towards "+
            "tried "+
            "tries "+
            "truly "+
            "try "+
            "trying "+
            "twice "+
            "two "+
            "u "+
            "un "+
            "under "+
            "unfortunately "+
            "unless "+
            "unlikely "+
            "until "+
            "unto "+
            "up "+
            "upon "+
            "us "+
            "use "+
            "used "+
            "useful "+
            "uses "+
            "using "+
            "usually "+
            "uucp "+
            "v "+
            "value "+
            "various "+
            "very "+
            "via "+
            "viz "+
            "vs "+
            "w "+
            "want "+
            "wants "+
            "was "+
            "wasn't "+
            "way "+
            "we "+
            "we'd "+
            "we'll "+
            "we're "+
            "we've "+
            "welcome "+
            "well "+
            "went "+
            "were "+
            "weren't "+
            "what "+
            "what's "+
            "whatever "+
            "when "+
            "whence "+
            "whenever "+
            "where "+
            "where's "+
            "whereafter "+
            "whereas "+
            "whereby "+
            "wherein "+
            "whereupon "+
            "wherever "+
            "whether "+
            "which "+
            "while "+
            "whither "+
            "who "+
            "who's "+
            "whoever "+
            "whole "+
            "whom "+
            "whose "+
            "why "+
            "will "+
            "willing "+
            "wish "+
            "with "+
            "within "+
            "without "+
            "won't "+
            "wonder "+
            "would "+
            "would "+
            "wouldn't "+
            "x "+
            "y "+
            "yes "+
            "yet "+
            "you "+
            "you'd "+
            "you'll "+
            "you're "+
            "you've "+
            "your "+
            "yours "+
            "yourself "+
            "yourselves "+
            "z "+
            "zero";

    /**
     * Checks if the word is a stopword
     * @param word
     * @return
     */
    protected boolean isNorwegianStopWord(String word) {
        return norwegianStopWords.contains(word);
    }

    /**
     * Checks if the word is a stopword
     * @param word
     * @return
     */
    protected boolean isEnglishStopWord(String word) {
        return englishStopWords.contains(word);
    }

    /**
     * Self explainatory?
     * @param word
     * @return
     */
    public boolean isStopWord(String word) {
        return (isEnglishStopWord(word) || isNorwegianStopWord(word));
    }
}
