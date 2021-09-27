package com.example.a1.whereami.SubwaySystem;

import java.util.ArrayList;

public class SubwaySerch {


    SubwaySerch() {

    }

    ArrayList<SubwayRoute> serch(String start_k_name, String end_k_name) {

        int m = 10000;
        int i, j, k = 0, s, e, min;
        int[] v = new int[111];
        int[] distance = new int[111];
        int[] via = new int[111];
        int[][] data = new int[111][111];

        ArrayList<SubwayRoute> subwayRoutelist = null;
        ArrayList<Integer> time_count = new ArrayList<>();
        ArrayList<String> name_count = new ArrayList<>();

        for (int count = 0; count < 111; count++) {
            for (int num = 0; num < 111; num++) {
                if (num == count) {
                    data[count][num] = 0;
                } else if (num != count) {
                    data[count][num] = m;
                }
            }
        }
        data[0][1] = 200;      // 다대포
        data[1][0] = 200;      // 다대포항
        data[1][2] = 140;
        data[2][1] = 140;      //낫개
        data[2][3] = 140;
        data[3][2] = 140;      //신장림
        data[3][4] = 140;
        data[4][3] = 140;      //장림
        data[4][5] = 200;
        data[5][4] = 200;      //동매
        data[5][6] = 210;
        data[6][5] = 210;      //신평
        data[6][7] = 140;
        data[7][6] = 180;      //하단
        data[7][8] = 100;
        data[8][7] = 950;      //당리
        data[8][9] = 100;
        data[9][8] = 100;      //사하
        data[9][10] = 95;
        data[10][9] = 110;     //괴정
        data[10][11] = 95;
        data[11][10] = 125;     //대티
        data[11][12] = 130;
        data[12][11] = 150;     //서대신
        data[12][13] = 135;
        data[13][12] = 150;     //동대신
        data[13][14] = 145;
        data[14][13] = 145;     //토성
        data[14][15] = 130;
        data[15][14] = 140;      //자갈치
        data[15][16] = 95;
        data[16][15] = 95;       //남포
        data[16][17] = 110;
        data[17][16] = 110;       //중앙
        data[17][18] = 115;
        data[18][17] = 110;       //부산
        data[18][19] = 90;
        data[19][18] = 95;       //초량
        data[19][20] = 90;
        data[20][19] = 95;       //부산진
        data[20][21] = 105;
        data[21][20] = 100;      //좌천
        data[21][22] = 105;
        data[22][21] = 105;       //범일
        data[22][23] = 95;
        data[23][22] = 100;       //범내골
        data[23][24] = 130;
        data[24][23] = 135 ;       //서면
        data[24][25] = 115 ;
        data[24][108] =120 ;
        data[25][24] = 110;       //부전
        data[25][26] = 125;
        data[26][25] = 125;       //양정
        data[26][27] = 100;
        data[27][26] = 105;        //시청
        data[27][28] = 105;
        data[28][27] = 125;       //연산
        data[28][29] = 135;
        data[28][109] = 180;
        data[29][28] = 115;       //교대
        data[29][30] = 115;
        data[30][29] = 120;       //동래
        data[30][31] = 100;
        data[30][88] = 135;
        data[30][96] = 95;
        data[31][30] = 95;         //명륜
        data[31][32] = 105;
        data[32][31] = 115;         //온천장
        data[32][33] = 115;
        data[33][32] = 115;         // 부산대
        data[33][34] = 110;
        data[34][33] = 110;          //장전
        data[34][35] = 125;
        data[35][34] = 105;          //구서
        data[35][36] = 110;
        data[36][35] = 105;         //두실
        data[36][37] = 95;
        data[37][36] = 95;         //남산
        data[37][38] = 105;
        data[38][37] = 105;        //범어서
        data[38][39] = 145;
        data[39][38] = 110;         //노포
        data[40][41] = 90;          //장산
        data[41][40] = 125;         //중동
        data[41][42] = 90;
        data[42][41] = 115;         //해운대
        data[42][43] = 135;
        data[43][42] = 125;         //동백
        data[43][44] = 115;
        data[44][43] = 115;         //백스코
        data[44][45] = 100;
        data[45][44] = 105;         //센턴시티
        data[45][46] = 120;
        data[46][45] = 120;         //민락
        data[46][47] = 155;
        data[47][46] = 155;         //수영
        data[47][48] = 105 ;
        data[47][110] = 180 ;
        data[48][47] = 100;          //광안
        data[48][49] = 100;
        data[49][48] = 95;          //금련산
        data[49][50] = 105;
        data[50][49] = 105;         //남천
        data[50][51] = 95;
        data[51][50] = 105;          //경성대,부경대
        data[51][52] = 105;
        data[52][51] = 95;           //대연
        data[52][53] = 90;
        data[53][52] = 90;          //못골
        data[53][54] = 95;
        data[54][53] = 110;           //지게골
        data[54][55] = 115;
        data[55][54] = 110;            //문련
        data[55][56] = 100;
        data[56][55] = 100;           //국제금융센터,부산은행
        data[56][57] = 100;
        data[57][56] = 105;          // 전포
        data[57][108] = 135;
        data[58][108] = 100;         //부암
        data[58][59] = 95;
        data[59][58] = 95;           //가야
        data[59][60] = 100;
        data[60][59] = 100;         //동의대
        data[60][61] = 110;
        data[61][60] = 110;         //개금
        data[61][62] = 105;
        data[62][61] = 110;         //냉정
        data[62][63] = 95;
        data[63][62] = 95;           //주례
        data[63][64] = 125;
        data[64][63] = 125;          //감전
        data[64][65] = 125;
        data[65][64] = 135;         //사상
        data[65][66] = 135;
        data[66][65] = 130;         //덕포
        data[66][67] = 95;
        data[67][66] = 95;            //모덕
        data[67][68] = 105;
        data[68][67] = 110;           //모라
        data[68][69] = 115;
        data[69][68] = 110;          //구남
        data[69][70] = 95;
        data[70][69] = 95;            //구명
        data[70][71] = 150;
        data[71][70] = 155;          //덕천
        data[71][72] = 155;
        data[71][91] = 115;
        data[71][92] = 160;
        data[72][71] = 145;           //수정
        data[72][73] = 150;
        data[73][72] = 155;          //화명
        data[73][74] = 130;
        data[74][73] = 115;           //율리
        data[74][75] = 125;
        data[75][74] = 125;          //동원
        data[75][76] = 105;
        data[76][75] = 105;           //금곡
        data[76][77] = 165;
        data[77][76] = 130;           //호포
        data[77][78] = 300;
        data[78][77] = 280;          //증산
        data[78][79] = 90;
        data[79][78] = 90;            //부산대 양산캠퍼스
        data[79][80] = 110;
        data[80][79] = 140;           // 남양산
        data[80][81] = 170;
        data[81][80] = 140;             //양산
        data[82][110] = 145;            //망미
        data[82][83] = 110;
        data[83][82] = 120;            //배산
        data[83][84] = 135;
        data[84][83] = 125;             //물만골
        data[84][109] = 125;
        data[85][109] = 95;             //거제
        data[85][86] = 90;
        data[86][85] = 105;            //종합운동장
        data[86][87] = 110;
        data[87][86] = 105;            //사직
        data[87][88] = 115;
        data[88][30] = 240;
        data[88][87] = 125;             //미남
        data[88][89] = 260;
        data[89][88] = 225;            //만덕
        data[89][90] = 120;
        data[90][89] = 115;            //남산정
        data[90][91] = 105;
        data[91][90] = 105;            //숙등
        data[91][71] = 90;
        data[92][71] = 140;             //구포
        data[92][93] = 165;
        data[93][92] = 155;            //강서구청
        data[93][94] = 110;
        data[94][93] = 110;            //체육공원
        data[94][95] = 115;
        data[95][94] = 75;            //대저
        data[96][90] = 90;            // 수안
        data[96][97] = 90;
        data[97][96] = 90;             //낙민
        data[97][98] = 100;
        data[98][97] = 100;            //충렬사
        data[98][99] = 95;
        data[99][98] = 90;            //명장
        data[99][100] = 135;
        data[100][99] = 135;          //서동
        data[100][101] = 100;
        data[101][100] = 105;         //금사
        data[101][102] = 100;
        data[102][101] = 100;         //반여농산물시장
        data[102][103] = 125;
        data[103][102] = 125;         //석대
        data[103][104] = 140;
        data[104][103] = 140;         //영산대
        data[104][105] = 125;
        data[105][104] = 125;        //동부산대학
        data[105][106] = 100;
        data[106][105] = 100;         //고촌
        data[106][107] = 130;
        data[107][106] = 135;          //안평
        data[108][24] = 120;           //서면 2호선
        data[108][57] = 165;
        data[108][58] = 130;
        data[109][28] = 180;         //연산 3호선
        data[109][84] = 150;
        data[109][85] =125;
        data[110][47] =180;         // 수영 3호선
        data[110][82] = 140;
        String temp;
        temp =  start_k_name;
        s = getIndex( temp );    //추출
        temp = end_k_name ;
        e = getIndex( temp ); //추출

        int time_total = 0;

        // 출발점이나 도착점의 입력값을 받을때도 알파벳을 숫자로 대체한 노드 값을 받음
        for (j = 0; j < 111; j++) {
            v[j] = 0;            //상태를 초기화함
            distance[j] = m;    //거리를 최대값으로 설정
        }
        distance[s] = 0; //출발점을 0으로 초기화

        for (i = 0; i < 111; i++) {
            min = m;
            for (j = 0; j < 111; j++) {
                if (v[j] == 0 && distance[j] < min) { //연결 되어있는 인접한 노드들 중에 최단거리를 찾음
                    k = j;
                    min = distance[j];
                }
            }
            v[k] = 1;                    //최단거리를 설정
            if (min == m) break;
            //최단거리가 아닐 경우

            for (j = 0; j < 111; j++) {
                if (distance[j] > distance[k] + data[k][j]) {    // s에서 k를 지나 j까지 가는 거리
                    distance[j] = distance[k] + data[k][j]; // 그 거리를 최단거리로 설정하고 저장함
                    via[j] = k;
                }
            }
        }


        int path[] = new int[111];
        int path_cnt = 0;
        k = e;

        while (true) {
            path[path_cnt++] = k;
            if (k == s) break;
            k = via[k];
        }
        for (i = path_cnt - 1; i >= 0; i--) {
            if (i == 0) {
                //System.out.println(STATE[path[i]]); break;
                time_count.add( distance[path[i]]);
                name_count.add(STATE[path[i]]);
                break;
            }
            time_count.add( distance[path[i]]);
            name_count.add(STATE[path[i]]);
            //System.out.print(STATE[path[i]]+" -> ");
        }



        int count;
        count = 0;

        ArrayList<Integer> time_path = new ArrayList();
        while (true) {
            if(count == 0 ) {
                time_path.add( 0 );
            } else if (count < time_count.size()) {
                time_path.add( time_count.get( count ) - time_count.get( count - 1 ) );
            } else {
                break;
            }
            count ++;
        }
        subwayRoutelist = new ArrayList<>();

        count = 0;
        while (true) {
            if (count < name_count.size()) {
                subwayRoutelist.add( new SubwayRoute( name_count.get( count ), time_path.get( count ) ) );
            } else {
                break;
            }  count++;
        }
        for(int num =0; num< subwayRoutelist.size()-1; num++) {
            if(subwayRoutelist.get( num ).station.equals("서면") && subwayRoutelist.get(num+1).station.equals( "서면2" )) {
                subwayRoutelist.remove(num+1);
            } else if (subwayRoutelist.get( num).station.equals( "서면2" ) && subwayRoutelist.get( num+1).station.equals( "서면" )){
                subwayRoutelist.remove( num );
            } else if(subwayRoutelist.get( num ).station.equals("연산") && subwayRoutelist.get(num+1).station.equals( "연산3" )) {
                subwayRoutelist.remove(num+1);
            } else if (subwayRoutelist.get( num).station.equals( "연산3" ) && subwayRoutelist.get( num+1).station.equals( "연산" )) {
                subwayRoutelist.remove( num );
            } else if(subwayRoutelist.get( num ).station.equals("수영") && subwayRoutelist.get(num+1).station.equals( "수영3" )) {
                subwayRoutelist.remove(num+1);
            } else if (subwayRoutelist.get( num).station.equals( "수영3" ) && subwayRoutelist.get( num+1).station.equals( "수영" )) {
                subwayRoutelist.remove( num );
            }
        }
        return subwayRoutelist;
    }
    static final String[] STATE = {"다대포해수욕장", "다대포항", "낫개", "신장림", "장림", "동매", "신평", "하단", "당리", "사하", "괴정", "대티", "서대신", "동대신", "토성", "자갈치", "남포", "중앙", "부산", "초량", "부산진", //21
            "좌천", "범일", "범내골", "서면", "부전", "양정", "시청", "연산", "교대", "동래", "명륜", "온천장", "부산대", "장전", "구서", "두실", "남산", "범어사", "노포", // 19
            "장산", "중동", "해운대", "동백", "벡스코", "센텀시티", "민락", "수영", "광안", "금련산", "남천", "경성대.부경대", "대연", "못골", "지게골", "문현", "국제금융센터·부산은행", "전포", "부암", //19
            "가야", "동의대", "개금", "냉정", "주례", "감전", "사상", "덕포", "모덕", "모라", "구남", "구명", "덕천", "수정", "화명", "율리", "동원", "금곡", "호포", "증산", "부산대양산캠퍼스", "남양산", "양산", "망미", "배산", //25
            "물만골", "거제", "종합운동장", "사직", "미남", "만덕", "남산정", "숙등", "구포", "강서구청", "체육공원", "대저", "수안", "낙민", "충렬사", "명장", "서동", "금사", "반여농산물시장", "석대", "영산대", "동부산대학", "고촌", "안평", //24
            "서면2", "연산3","수영3"}; //3

    static int getIndex(String temp) {
        int result = -1;
        for (int i = 0; i < STATE.length; i++) {
            if (STATE[i].equals( temp )) {
                result = i;
                break;
            }
        }
        return result;
    }
}





