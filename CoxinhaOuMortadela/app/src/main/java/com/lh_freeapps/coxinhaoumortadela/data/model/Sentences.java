package com.lh_freeapps.coxinhaoumortadela.data.model;

/**
 * Created by Leandro on 15/04/2017.
 *
 * In-memory Data (fastest solution)
 */
public abstract class Sentences {


    // 20 sentences from 0 to 19
    // even questions are mortadelas
    // odd  questions are coxinhas
    private static final String[] sentences = new String[] {
            "Programas assistencialistas como o Bolsa Família devem ser ampliados"                 , //#00
            "Sou a favor da redução da menor idade penal"                                          , //#01
            "Pessoas com mais dinheiro devem pagar mais impostos"                                  , //#02
            "Sou a favor da prisão perpétua ou pena de morte"                                      , //#03
            "Sou a favor da descriminalização da maconha"                                          , //#04
            "É importante que o governo invista mais nas forças armadas"                           , //#05
            "Sou a favor do casamento civil homoafetivo"                                           , //#06
            "Sou a favor da menor intervenção do governo na economia"                              , //#07
            "Sou a favor de cotas para negros em concursos públicos"                               , //#08
            "Sou a favor da privatização ou concesão em determinados setores"                      , //#09
            "Sociologia e Filosofia devem ser disciplinas obrigatórias em escolas"                 , //#10
            "A polícia pode invadir residências sem ordens judiciais quando houver razões fundadas", //#11
            "O regime capitalista é opressivo e deve ser substituído por um mais igualitário"      , //#12
            "Sou a favor do porte de armas"                                                        , //#13
            "O governo tem o direito de comedir propagandas de cervejas, cigarros e armas"         , //#14
            "Sou a favor de leis mais rigorosas contra criminosos"                                 , //#15
            "Sou a favor de cotas para universidades públicas"                                     , //#16
            "É papel da família ensinar valores morais, não da escola"                             , //#17
            "Negros e homossexuais devem ter leis protetivas diferenciadas na legislação"          , //#18
            "O crescimento econônimo do indivíduo depende primordialmente do esforço pessoal"        //#19
    };


    public static String getSentence(int sentence) {
        return sentences[sentence];
    }

    public static int getMaxSentenceNumber() { return sentences.length; }

    public static boolean isCoxinha(int sentenceNumber) { return sentenceNumber % 2 != 0; }

}
