/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.util;

/**
 *
 * @author dolivo
 */
public class Etichette {
    
    // SG START
    String addUserOK;
    String addUserKO;

    //header
    String header1_profilo, header1_logout;
    //footer
    String footer;

    //menu
    String home, abuses, games, operators, players, publishers, rewards, stats;
    String abuses_search, abuses_report;
    String games_cat_add, games_cat_mgmt, games_add, games_search;
    String operators_add, operators_search;
    String players_search;
    String publishers_add, publishers_search;
    String rewards_cat_add, rewards_cat_mgmt, rewards_add, rewards_search, rewards_report;
    String stats_user, stats_act_user;

    //index
    String index_pannello, index_rewards, index_games, index_players, index_advs, index_activities, index_gamesw, index_rewardsw, index_abusesw, index_gogame, index_goadv, index_goplayer, index_goreward;

    //abuse
    String attention, type, status, results, actions, destinated, desc, creation, update, user;
    //msg
    String msg_close, msg_noclose, msg_processed, msg_noprocessed, msg_ban, msg_noban, msg_activate, msg_noactivate;
    String title_confclose, title_confactivate, title_confban, title_confprocess, msg_confclose, msg_confactivate, msg_confprocess, msg_confban, msg_ricerca;

    //game
    String campoobl, cattrans, catdef, langdef, game, category, gamename;
    String msg_gamecatko, msg_gamecatok, msg_gamcatext, oblig_radio, oblig_radiod;
    String listcg, nome, show, info;
    String title_confatt, msg_confatt, title_confdisatt, msg_confdisatt;
    String publisher, earning, Highscore;
    String msg_gameactok, msg_gameactko, msg_gamedisok, msg_gamedisko;
    String storelink, playstore, ios, packageg, version, updateg, imgandroid, image, remove, change, imgios, nota, descg, alertgame, obl_sl;
    String msg_gameok, msg_gameko, msg_gameimg, msg_gamemodko, msg_gamemodok, desctrans, edit;

    //show
    String Show_overview, Show_generals, Show_Earnings, Show_Name, Show_Desc, Show_Details, Show_Player, Show_EarningsP, Show_Score, Show_DL, Show_Players, Show_Total, Show_Payments, Show_Account;

    //operator
    String op, op_fname, op_lname, op_lang, op_email, op_phone, op_sms, op_username, op_seltype, op_selstatus, op_selphone, op_control, op_emailcontrol;
    String op_title_dis, op_msg_dis, op_title_act, op_msg_act, op_disok, op_disko, op_actok, op_actko, op_modok, op_modko;
    //login
    String accedi, username, password, login, resetpassword, error_log1, messagereset1, email, indietro_log, avanti_log, cambiapsw, chiudi;

    //publisher
    String pb_nome, pb_link, pb_control, pb_modok, pb_modko, pb_insok, pb_insko, pb_title_dis, pb_msg_dis, pb_title_act, pb_msg_act, pb_changestatus, pb_disok, pb_disko, pb_actok, pb_actko;
    ;
    
    //player
    String p_username, p_lname, p_fname, p_status, p_email, p_phone, p_ranking, p_contry, p_online, p_date, p_earnings, p_show_pi;
    String pshow_generals, pshow_gen_name, pshow_gen_description, pshow_gen_details;
    String pshow_gen_rank, pshow_gen_earn, pshow_gen_not, pshow_gen_chall, pshow_gen_device, pshow_gen_playtime, pshow_gen_specials, pshow_gen_viewtime, pshow_gen_friends, pshow_gen_rew, pshow_gen_rep, pshow_gen_eatot;
    String pshow_gen_rank_desc, pshow_gen_earn_desc, pshow_gen_not_desc, pshow_gen_chall_desc, pshow_gen_device_desc, pshow_gen_playtime_desc, pshow_gen_specials_desc, pshow_gen_viewtime_desc, pshow_gen_friends_desc, pshow_gen_rew_desc, pshow_gen_rep_desc, pshow_gen_eatot_desc;
    String pshow_earnings, pshow_ea_game, pshow_ea_totearnings, pshow_ea_totscore;
    String pshow_notifications, pshow_not_type, pshow_not_date, pshow_not_status;
    String pshow_specials, pshow_sp_name, pshow_sp_date, pshow_sp_credits, pshow_view;
    String pshow_rewards, pshow_rew_name, pshow_rew_date, pshow_rew_credits, pshow_rew_status;
    String p_title_confactivate, p_msg_confactivate, p_msg_noactivate, p_msg_activate, p_title_confban, p_msg_confban, p_msg_ban, p_msg_noban;

    //profilo
    String pass2_msg, psw1_msg, psw_msg, actualpsw_msg, fname_msg, lname_msg, email_msg, user_msg, phone_msg, sms_msg, editko, editok, passko, passok, passerr;

    //notifiche
    String not_msg, not, notok, notko, sendto, send, nottosend, not_showabuses, not_showall, notsendfor;

    //titolo
    String actualpsw, newpsw, newpsw1, cambiopswok, cambiopsw_error, modificapsw, cambiopassword, salva, salvadatiok, salvadatierror, schermobloccato, cambiapasswordfirstaccess, cambiopswok1;
    String savecontactok, savecontactko, saveappuntamentook, saveappuntamentoko;
    String messagebanned, banned, messageerror, resetpsw, pswdontmatch, epsw, formatpsw, pswinfo, errorresetpsw, titolo;

    //info
    String data, history;

    //rewards
    String r_reward, r_sales, r_price, r_availables, listcr, msg_gamrewext, msg_cat_editko, msg_cat_editok, oblig_radiot;
    String r_name, r_cat, r_datarange, r_datasel, r_status, r_pricerange, r_awarded, r_quantity, r_datefrom, r_dateto, r_image;
    String r_title_confatt, r_msg_confatt, r_title_confdisatt, r_msg_confdisatt;
    String r_msg_rewactok, r_msg_rewactko, r_msg_rewdisok, r_msg_rewdisko;
    String r_desctrans,r_namedef,r_img,r_desc,r_exist,r_rew,r_descriztr;
    String r_msg_reweok ,r_msg_rewko,r_msg_rewmodko,r_msg_rewmodok;
    public Etichette(String la) {

        if (la.equals("IT")) {
            //HEADER FOOTER
            
            header1_profilo = "Profilo Personale";
            header1_logout = "LogOut";

            //LOGIN 
            accedi = "<center>LOGIN</center>";
            error_log1 = "Inserire Username e Password";
            resetpassword = "Password Dimenticata?";
            username = "Username";
            password = "Password";
            email = "Email";
            login = "LOGIN";
            messagereset1 = "Inserire Username ed Email per continuare";
            banned = "Accesso Negato";
            messagebanned = "Impossibile accedere, permesso negato.";
            indietro_log = "Indietro";
            avanti_log = "Conferma";
            StringBuilder em = new StringBuilder();
            em.append("Username e/o password errati: <br>");
            em.append("Riprovare o fare click su \"Password Dimenticata\" per reimpostarla");
            messageerror = em.toString();

            StringBuilder cp = new StringBuilder();
            cp.append("Primo Accesso: <br>");
            cp.append("E' necessario modificare la password");
            cambiapasswordfirstaccess = cp.toString();

            resetpsw = "Reset Password";
            pswdontmatch = "Le Password non corrispondono. Ripovare Nuovamente.";
            epsw = "Errore durante il Cambio Password. Ripovare Nuovamente.";
            
            
            StringBuilder sb = new StringBuilder();
            sb.append("Requisiti Nuova Password: <br/>\n");
            sb.append(" - lunghezza (> 7 caratteri); <br/>\n");
            sb.append(" - almeno una lettera maiuscola (A,B,...); <br/>\n");
            sb.append(" - almeno una lettera minuscola (a,b,...); <br/>\n");
            sb.append(" - almeno un numero (0-9); <br/>\n");
            sb.append(" - almeno un carattere speciale (@,#,...)");
            pswinfo = sb.toString();
            cambiapsw = "Cambia Password";
            
            formatpsw = "Formato Password non Valido. Ripovare Nuovamente. <br/><br/>\n"+pswinfo;
            

            actualpsw = "Password Attuale";
            newpsw = "Nuova Password";
            newpsw1 = "Conferma Password";

            errorresetpsw = "Password o Username errati. Ripovare Nuovamente";
            cambiapasswordfirstaccess = "Primo accesso, è necessario modificare la Password.";
            
            
            
            
            chiudi = "Chiudi";
            
            campoobl = "Campo Obbligatorio";
            
            addUserOK = "Operatore Aggiunto con Successo!";
            addUserKO = "Errore nell'inserimento dell'Operatore. Riprovare";
            op_fname = "Nome";
            op_lname = "Cognome";
            op_lang = "Lingua";
            op_email = "Email";
            op_phone = "Telefono";
            op_sms = "Possibilità di ricevere SMS";
            op_username = "Username";
            op_seltype = "Selezionare un Tipo di Operatore";
            op_selphone = "Inserire un numero di Telefono valido!";
            op_selstatus = "Selezionare uno Stato!";
            op = "Operatore";
            op_control = "Username o Email già in uso. Riprova!";
            op_emailcontrol = "Email già in Uso!";
            op_title_dis = "Disattivazione Operatore";
            op_msg_dis = "Sicuro di voler Disattivare questo Operatore?";
            op_title_act = "Attivazione Operatore";
            op_msg_act = "Sicuro di voler Attivare questo Operatore?";
            op_disok = "Operatore Disattivato con Successo!";
            op_disko = "Errore durante la Disattivazione dell'Operatore. Riprovare! ";
            op_actok = "Operatore Attivato con Successo!";
            op_actko = "Errore durante l'ttivazione dell'Operatore. Riprovare! ";
            op_modok = "Operatore Modificato con Successo!";
            op_modko = "Errore durante la Modifica dell'Operatore. Riprovare! ";
            //PROFILE
            cambiopassword = "Cambio Password";
            pass2_msg = "Le Passwords non Corrispondono!";
            psw1_msg = "Il campo Conferma Password non può essere vuoto";
            psw_msg = "Il campo Nuova Password non può essere vuoto";
            actualpsw_msg = "Il campo Password Attuale non può essere vuoto";
            fname_msg = "Il campo Nome non può essere vuoto";
            lname_msg = "Il campo Cognome non può essere vuoto";
            email_msg = "Il campo Email non può essere vuoto";
            user_msg = "Il campo Username non può essere vuoto";
            phone_msg = "Il campo Telefono non può essere vuoto e la lunghezza deve essere di almeno 7 caratteri";
            sms_msg = "Per poter abilitare il Check Sms bisogna inserire un numero di Telefono valido";
            editko = "Errore durante la Modifica delle Informazioni del Profilo. Riprovare!";
            editok = "Modifica Informazioni del Profilo avvenuta con Successo! ";
            passko = "Errore durante la Modifica della Password. Riprovare!";
            passok = "Modifica Password avvenuta con Successo!";
            passerr = "La Password Inserita non corrisponde a quella attualmente in Uso";
            footer = " Rendicontazione v 1.0";
            //publisher
            //INFO.jsp
            data = "Data";
            history = "Storia dei Cambi di Stato";

         
        }//if IT

        
    }//costruttore

    public String getAddUserOK() {
        return addUserOK;
    }

    public String getAddUserKO() {
        return addUserKO;
    }

    public String getHeader1_profilo() {
        return header1_profilo;
    }

    public String getHeader1_logout() {
        return header1_logout;
    }

    public String getFooter() {
        return footer;
    }

    public String getHome() {
        return home;
    }

    public String getAbuses() {
        return abuses;
    }

    public String getGames() {
        return games;
    }

    public String getOperators() {
        return operators;
    }

    public String getPlayers() {
        return players;
    }

    public String getPublishers() {
        return publishers;
    }

    public String getRewards() {
        return rewards;
    }

    public String getStats() {
        return stats;
    }

    public String getAbuses_search() {
        return abuses_search;
    }

    public String getAbuses_report() {
        return abuses_report;
    }

    public String getGames_cat_add() {
        return games_cat_add;
    }

    public String getGames_cat_mgmt() {
        return games_cat_mgmt;
    }

    public String getGames_add() {
        return games_add;
    }

    public String getGames_search() {
        return games_search;
    }

    public String getOperators_add() {
        return operators_add;
    }

    public String getOperators_search() {
        return operators_search;
    }

    public String getPlayers_search() {
        return players_search;
    }

    public String getPublishers_add() {
        return publishers_add;
    }

    public String getPublishers_search() {
        return publishers_search;
    }

    public String getRewards_cat_add() {
        return rewards_cat_add;
    }

    public String getRewards_cat_mgmt() {
        return rewards_cat_mgmt;
    }

    public String getRewards_add() {
        return rewards_add;
    }

    public String getRewards_search() {
        return rewards_search;
    }

    public String getRewards_report() {
        return rewards_report;
    }

    public String getStats_user() {
        return stats_user;
    }

    public String getStats_act_user() {
        return stats_act_user;
    }

    public String getIndex_pannello() {
        return index_pannello;
    }

    public String getIndex_rewards() {
        return index_rewards;
    }

    public String getIndex_games() {
        return index_games;
    }

    public String getIndex_players() {
        return index_players;
    }

    public String getIndex_advs() {
        return index_advs;
    }

    public String getIndex_activities() {
        return index_activities;
    }

    public String getIndex_gamesw() {
        return index_gamesw;
    }

    public String getIndex_rewardsw() {
        return index_rewardsw;
    }

    public String getIndex_abusesw() {
        return index_abusesw;
    }

    public String getIndex_gogame() {
        return index_gogame;
    }

    public String getIndex_goadv() {
        return index_goadv;
    }

    public String getIndex_goplayer() {
        return index_goplayer;
    }

    public String getIndex_goreward() {
        return index_goreward;
    }

    public String getAttention() {
        return attention;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getResults() {
        return results;
    }

    public String getActions() {
        return actions;
    }

    public String getDestinated() {
        return destinated;
    }

    public String getDesc() {
        return desc;
    }

    public String getCreation() {
        return creation;
    }

    public String getUpdate() {
        return update;
    }

    public String getUser() {
        return user;
    }

    public String getMsg_close() {
        return msg_close;
    }

    public String getMsg_noclose() {
        return msg_noclose;
    }

    public String getMsg_processed() {
        return msg_processed;
    }

    public String getMsg_noprocessed() {
        return msg_noprocessed;
    }

    public String getMsg_ban() {
        return msg_ban;
    }

    public String getMsg_noban() {
        return msg_noban;
    }

    public String getMsg_activate() {
        return msg_activate;
    }

    public String getMsg_noactivate() {
        return msg_noactivate;
    }

    public String getTitle_confclose() {
        return title_confclose;
    }

    public String getTitle_confactivate() {
        return title_confactivate;
    }

    public String getTitle_confban() {
        return title_confban;
    }

    public String getTitle_confprocess() {
        return title_confprocess;
    }

    public String getMsg_confclose() {
        return msg_confclose;
    }

    public String getMsg_confactivate() {
        return msg_confactivate;
    }

    public String getMsg_confprocess() {
        return msg_confprocess;
    }

    public String getMsg_confban() {
        return msg_confban;
    }

    public String getMsg_ricerca() {
        return msg_ricerca;
    }

    public String getCampoobl() {
        return campoobl;
    }

    public String getCattrans() {
        return cattrans;
    }

    public String getCatdef() {
        return catdef;
    }

    public String getLangdef() {
        return langdef;
    }

    public String getGame() {
        return game;
    }

    public String getCategory() {
        return category;
    }

    public String getGamename() {
        return gamename;
    }

    public String getMsg_gamecatko() {
        return msg_gamecatko;
    }

    public String getMsg_gamecatok() {
        return msg_gamecatok;
    }

    public String getMsg_gamcatext() {
        return msg_gamcatext;
    }

    public String getOblig_radio() {
        return oblig_radio;
    }

    public String getOblig_radiod() {
        return oblig_radiod;
    }

    public String getListcg() {
        return listcg;
    }

    public String getNome() {
        return nome;
    }

    public String getShow() {
        return show;
    }

    public String getInfo() {
        return info;
    }

    public String getTitle_confatt() {
        return title_confatt;
    }

    public String getMsg_confatt() {
        return msg_confatt;
    }

    public String getTitle_confdisatt() {
        return title_confdisatt;
    }

    public String getMsg_confdisatt() {
        return msg_confdisatt;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getEarning() {
        return earning;
    }

    public String getHighscore() {
        return Highscore;
    }

    public String getMsg_gameactok() {
        return msg_gameactok;
    }

    public String getMsg_gameactko() {
        return msg_gameactko;
    }

    public String getMsg_gamedisok() {
        return msg_gamedisok;
    }

    public String getMsg_gamedisko() {
        return msg_gamedisko;
    }

    public String getStorelink() {
        return storelink;
    }

    public String getPlaystore() {
        return playstore;
    }

    public String getIos() {
        return ios;
    }

    public String getPackageg() {
        return packageg;
    }

    public String getVersion() {
        return version;
    }

    public String getUpdateg() {
        return updateg;
    }

    public String getImgandroid() {
        return imgandroid;
    }

    public String getImage() {
        return image;
    }

    public String getRemove() {
        return remove;
    }

    public String getChange() {
        return change;
    }

    public String getImgios() {
        return imgios;
    }

    public String getNota() {
        return nota;
    }

    public String getDescg() {
        return descg;
    }

    public String getAlertgame() {
        return alertgame;
    }

    public String getObl_sl() {
        return obl_sl;
    }

    public String getMsg_gameok() {
        return msg_gameok;
    }

    public String getMsg_gameko() {
        return msg_gameko;
    }

    public String getMsg_gameimg() {
        return msg_gameimg;
    }

    public String getMsg_gamemodko() {
        return msg_gamemodko;
    }

    public String getMsg_gamemodok() {
        return msg_gamemodok;
    }

    public String getDesctrans() {
        return desctrans;
    }

    public String getEdit() {
        return edit;
    }

    public String getShow_overview() {
        return Show_overview;
    }

    public String getShow_generals() {
        return Show_generals;
    }

    public String getShow_Earnings() {
        return Show_Earnings;
    }

    public String getShow_Name() {
        return Show_Name;
    }

    public String getShow_Desc() {
        return Show_Desc;
    }

    public String getShow_Details() {
        return Show_Details;
    }

    public String getShow_Player() {
        return Show_Player;
    }

    public String getShow_EarningsP() {
        return Show_EarningsP;
    }

    public String getShow_Score() {
        return Show_Score;
    }

    public String getShow_DL() {
        return Show_DL;
    }

    public String getShow_Players() {
        return Show_Players;
    }

    public String getShow_Total() {
        return Show_Total;
    }

    public String getShow_Payments() {
        return Show_Payments;
    }

    public String getShow_Account() {
        return Show_Account;
    }

    public String getOp() {
        return op;
    }

    public String getOp_fname() {
        return op_fname;
    }

    public String getOp_lname() {
        return op_lname;
    }

    public String getOp_lang() {
        return op_lang;
    }

    public String getOp_email() {
        return op_email;
    }

    public String getOp_phone() {
        return op_phone;
    }

    public String getOp_sms() {
        return op_sms;
    }

    public String getOp_username() {
        return op_username;
    }

    public String getOp_seltype() {
        return op_seltype;
    }

    public String getOp_selstatus() {
        return op_selstatus;
    }

    public String getOp_selphone() {
        return op_selphone;
    }

    public String getOp_control() {
        return op_control;
    }

    public String getOp_emailcontrol() {
        return op_emailcontrol;
    }

    public String getOp_title_dis() {
        return op_title_dis;
    }

    public String getOp_msg_dis() {
        return op_msg_dis;
    }

    public String getOp_title_act() {
        return op_title_act;
    }

    public String getOp_msg_act() {
        return op_msg_act;
    }

    public String getOp_disok() {
        return op_disok;
    }

    public String getOp_disko() {
        return op_disko;
    }

    public String getOp_actok() {
        return op_actok;
    }

    public String getOp_actko() {
        return op_actko;
    }

    public String getOp_modok() {
        return op_modok;
    }

    public String getOp_modko() {
        return op_modko;
    }

    public String getAccedi() {
        return accedi;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getResetpassword() {
        return resetpassword;
    }

    public String getError_log1() {
        return error_log1;
    }

    public String getMessagereset1() {
        return messagereset1;
    }

    public String getEmail() {
        return email;
    }

    public String getIndietro_log() {
        return indietro_log;
    }

    public String getAvanti_log() {
        return avanti_log;
    }

    public String getCambiapsw() {
        return cambiapsw;
    }

    public String getChiudi() {
        return chiudi;
    }

    public String getPb_nome() {
        return pb_nome;
    }

    public String getPb_link() {
        return pb_link;
    }

    public String getPb_control() {
        return pb_control;
    }

    public String getPb_modok() {
        return pb_modok;
    }

    public String getPb_modko() {
        return pb_modko;
    }

    public String getPb_insok() {
        return pb_insok;
    }

    public String getPb_insko() {
        return pb_insko;
    }

    public String getPb_title_dis() {
        return pb_title_dis;
    }

    public String getPb_msg_dis() {
        return pb_msg_dis;
    }

    public String getPb_title_act() {
        return pb_title_act;
    }

    public String getPb_msg_act() {
        return pb_msg_act;
    }

    public String getPb_changestatus() {
        return pb_changestatus;
    }

    public String getPb_disok() {
        return pb_disok;
    }

    public String getPb_disko() {
        return pb_disko;
    }

    public String getPb_actok() {
        return pb_actok;
    }

    public String getPb_actko() {
        return pb_actko;
    }

    public String getP_username() {
        return p_username;
    }

    public String getP_lname() {
        return p_lname;
    }

    public String getP_fname() {
        return p_fname;
    }

    public String getP_status() {
        return p_status;
    }

    public String getP_email() {
        return p_email;
    }

    public String getP_phone() {
        return p_phone;
    }

    public String getP_ranking() {
        return p_ranking;
    }

    public String getP_contry() {
        return p_contry;
    }

    public String getP_online() {
        return p_online;
    }

    public String getP_date() {
        return p_date;
    }

    public String getP_earnings() {
        return p_earnings;
    }

    public String getP_show_pi() {
        return p_show_pi;
    }

    public String getPshow_generals() {
        return pshow_generals;
    }

    public String getPshow_gen_name() {
        return pshow_gen_name;
    }

    public String getPshow_gen_description() {
        return pshow_gen_description;
    }

    public String getPshow_gen_details() {
        return pshow_gen_details;
    }

    public String getPshow_gen_rank() {
        return pshow_gen_rank;
    }

    public String getPshow_gen_earn() {
        return pshow_gen_earn;
    }

    public String getPshow_gen_not() {
        return pshow_gen_not;
    }

    public String getPshow_gen_chall() {
        return pshow_gen_chall;
    }

    public String getPshow_gen_device() {
        return pshow_gen_device;
    }

    public String getPshow_gen_playtime() {
        return pshow_gen_playtime;
    }

    public String getPshow_gen_specials() {
        return pshow_gen_specials;
    }

    public String getPshow_gen_viewtime() {
        return pshow_gen_viewtime;
    }

    public String getPshow_gen_friends() {
        return pshow_gen_friends;
    }

    public String getPshow_gen_rew() {
        return pshow_gen_rew;
    }

    public String getPshow_gen_rep() {
        return pshow_gen_rep;
    }

    public String getPshow_gen_eatot() {
        return pshow_gen_eatot;
    }

    public String getPshow_gen_rank_desc() {
        return pshow_gen_rank_desc;
    }

    public String getPshow_gen_earn_desc() {
        return pshow_gen_earn_desc;
    }

    public String getPshow_gen_not_desc() {
        return pshow_gen_not_desc;
    }

    public String getPshow_gen_chall_desc() {
        return pshow_gen_chall_desc;
    }

    public String getPshow_gen_device_desc() {
        return pshow_gen_device_desc;
    }

    public String getPshow_gen_playtime_desc() {
        return pshow_gen_playtime_desc;
    }

    public String getPshow_gen_specials_desc() {
        return pshow_gen_specials_desc;
    }

    public String getPshow_gen_viewtime_desc() {
        return pshow_gen_viewtime_desc;
    }

    public String getPshow_gen_friends_desc() {
        return pshow_gen_friends_desc;
    }

    public String getPshow_gen_rew_desc() {
        return pshow_gen_rew_desc;
    }

    public String getPshow_gen_rep_desc() {
        return pshow_gen_rep_desc;
    }

    public String getPshow_gen_eatot_desc() {
        return pshow_gen_eatot_desc;
    }

    public String getPshow_earnings() {
        return pshow_earnings;
    }

    public String getPshow_ea_game() {
        return pshow_ea_game;
    }

    public String getPshow_ea_totearnings() {
        return pshow_ea_totearnings;
    }

    public String getPshow_ea_totscore() {
        return pshow_ea_totscore;
    }

    public String getPshow_notifications() {
        return pshow_notifications;
    }

    public String getPshow_not_type() {
        return pshow_not_type;
    }

    public String getPshow_not_date() {
        return pshow_not_date;
    }

    public String getPshow_not_status() {
        return pshow_not_status;
    }

    public String getPshow_specials() {
        return pshow_specials;
    }

    public String getPshow_sp_name() {
        return pshow_sp_name;
    }

    public String getPshow_sp_date() {
        return pshow_sp_date;
    }

    public String getPshow_sp_credits() {
        return pshow_sp_credits;
    }

    public String getPshow_view() {
        return pshow_view;
    }

    public String getPshow_rewards() {
        return pshow_rewards;
    }

    public String getPshow_rew_name() {
        return pshow_rew_name;
    }

    public String getPshow_rew_date() {
        return pshow_rew_date;
    }

    public String getPshow_rew_credits() {
        return pshow_rew_credits;
    }

    public String getPshow_rew_status() {
        return pshow_rew_status;
    }

    public String getP_title_confactivate() {
        return p_title_confactivate;
    }

    public String getP_msg_confactivate() {
        return p_msg_confactivate;
    }

    public String getP_msg_noactivate() {
        return p_msg_noactivate;
    }

    public String getP_msg_activate() {
        return p_msg_activate;
    }

    public String getP_title_confban() {
        return p_title_confban;
    }

    public String getP_msg_confban() {
        return p_msg_confban;
    }

    public String getP_msg_ban() {
        return p_msg_ban;
    }

    public String getP_msg_noban() {
        return p_msg_noban;
    }

    public String getPass2_msg() {
        return pass2_msg;
    }

    public String getPsw1_msg() {
        return psw1_msg;
    }

    public String getPsw_msg() {
        return psw_msg;
    }

    public String getActualpsw_msg() {
        return actualpsw_msg;
    }

    public String getFname_msg() {
        return fname_msg;
    }

    public String getLname_msg() {
        return lname_msg;
    }

    public String getEmail_msg() {
        return email_msg;
    }

    public String getUser_msg() {
        return user_msg;
    }

    public String getPhone_msg() {
        return phone_msg;
    }

    public String getSms_msg() {
        return sms_msg;
    }

    public String getEditko() {
        return editko;
    }

    public String getEditok() {
        return editok;
    }

    public String getPassko() {
        return passko;
    }

    public String getPassok() {
        return passok;
    }

    public String getPasserr() {
        return passerr;
    }

    public String getNot_msg() {
        return not_msg;
    }

    public String getNot() {
        return not;
    }

    public String getNotok() {
        return notok;
    }

    public String getNotko() {
        return notko;
    }

    public String getSendto() {
        return sendto;
    }

    public String getSend() {
        return send;
    }

    public String getNottosend() {
        return nottosend;
    }

    public String getNot_showabuses() {
        return not_showabuses;
    }

    public String getNot_showall() {
        return not_showall;
    }

    public String getNotsendfor() {
        return notsendfor;
    }

    public String getActualpsw() {
        return actualpsw;
    }

    public String getNewpsw() {
        return newpsw;
    }

    public String getNewpsw1() {
        return newpsw1;
    }

    public String getCambiopswok() {
        return cambiopswok;
    }

    public String getCambiopsw_error() {
        return cambiopsw_error;
    }

    public String getModificapsw() {
        return modificapsw;
    }

    public String getCambiopassword() {
        return cambiopassword;
    }

    public String getSalva() {
        return salva;
    }

    public String getSalvadatiok() {
        return salvadatiok;
    }

    public String getSalvadatierror() {
        return salvadatierror;
    }

    public String getSchermobloccato() {
        return schermobloccato;
    }

    public String getCambiapasswordfirstaccess() {
        return cambiapasswordfirstaccess;
    }

    public String getCambiopswok1() {
        return cambiopswok1;
    }

    public String getSavecontactok() {
        return savecontactok;
    }

    public String getSavecontactko() {
        return savecontactko;
    }

    public String getSaveappuntamentook() {
        return saveappuntamentook;
    }

    public String getSaveappuntamentoko() {
        return saveappuntamentoko;
    }

    public String getMessagebanned() {
        return messagebanned;
    }

    public String getBanned() {
        return banned;
    }

    public String getMessageerror() {
        return messageerror;
    }

    public String getResetpsw() {
        return resetpsw;
    }

    public String getPswdontmatch() {
        return pswdontmatch;
    }

    public String getEpsw() {
        return epsw;
    }

    public String getFormatpsw() {
        return formatpsw;
    }

    public String getPswinfo() {
        return pswinfo;
    }

    public String getErrorresetpsw() {
        return errorresetpsw;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getData() {
        return data;
    }

    public String getHistory() {
        return history;
    }

    public String getR_reward() {
        return r_reward;
    }

    public String getR_sales() {
        return r_sales;
    }

    public String getR_price() {
        return r_price;
    }

    public String getR_availables() {
        return r_availables;
    }

    public String getListcr() {
        return listcr;
    }

    public String getMsg_gamrewext() {
        return msg_gamrewext;
    }

    public String getMsg_cat_editko() {
        return msg_cat_editko;
    }

    public String getMsg_cat_editok() {
        return msg_cat_editok;
    }

    public String getOblig_radiot() {
        return oblig_radiot;
    }

    public String getR_name() {
        return r_name;
    }

    public String getR_cat() {
        return r_cat;
    }

    public String getR_datarange() {
        return r_datarange;
    }

    public String getR_datasel() {
        return r_datasel;
    }

    public String getR_status() {
        return r_status;
    }

    public String getR_pricerange() {
        return r_pricerange;
    }

    public String getR_awarded() {
        return r_awarded;
    }

    public String getR_quantity() {
        return r_quantity;
    }

    public String getR_datefrom() {
        return r_datefrom;
    }

    public String getR_dateto() {
        return r_dateto;
    }

    public String getR_image() {
        return r_image;
    }

    public String getR_title_confatt() {
        return r_title_confatt;
    }

    public String getR_msg_confatt() {
        return r_msg_confatt;
    }

    public String getR_title_confdisatt() {
        return r_title_confdisatt;
    }

    public String getR_msg_confdisatt() {
        return r_msg_confdisatt;
    }

    public String getR_msg_rewactok() {
        return r_msg_rewactok;
    }

    public String getR_msg_rewactko() {
        return r_msg_rewactko;
    }

    public String getR_msg_rewdisok() {
        return r_msg_rewdisok;
    }

    public String getR_msg_rewdisko() {
        return r_msg_rewdisko;
    }

    public String getR_desctrans() {
        return r_desctrans;
    }

    public String getR_namedef() {
        return r_namedef;
    }

    public String getR_img() {
        return r_img;
    }

    public String getR_desc() {
        return r_desc;
    }

    public String getR_exist() {
        return r_exist;
    }

    public String getR_rew() {
        return r_rew;
    }

    public String getR_descriztr() {
        return r_descriztr;
    }

    public String getR_msg_reweok() {
        return r_msg_reweok;
    }

    public String getR_msg_rewko() {
        return r_msg_rewko;
    }

    public String getR_msg_rewmodko() {
        return r_msg_rewmodko;
    }

    public String getR_msg_rewmodok() {
        return r_msg_rewmodok;
    }

}

   