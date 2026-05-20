package com.bwd.nms.service.util;

import com.bwd.nms.domain.NetworkState;
import com.bwd.nms.service.dto.AlertsStatusResponse;
import com.bwd.nms.service.dto.MCPAlarmsResponse;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SvgUtil {

    private final Logger log = LoggerFactory.getLogger(SvgUtil.class);

    @Autowired
    Environment env;

    private String PREFIX_TAG = "VT4(";

    private String ALARM_TAG = "-ALARM";

    private String SUFFIX_TAG = ")";

    private String SEARCH_TAG = "v:cp";

    private String SVG_TAG = "<svg";

    private String RECT_TAG = "rect";

    private String ERRORIMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAXIAAAFlCAMAAADBKZMYAAAABGdBTUEAALGPC/xhBQAAACBjSFJN AAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAC5VBMVEX/////9fX/6+v/4+P/ 3Nz/1tb/0tL/0ND/zs7/z8//1NT/19f/3t7/5eX/7u7/+Pj/5ub/y8v/sbH/mZn/goL/bW3/WVn/ Rkb/Njb/Jib/GBj/Cwv/Cgr/CQn/CAj/EBD/HBz/Kyv/Ojr/TU3/X1//dHT/iYn/oaH/ubn/7+// /v7/2tr/tbX/kZH/bm7/Tk7/Li7/Dw//Bwf/Bgb/BQX/BAT/AwP/AgL/AQH/AAD/Ghr/ODj/V1f/ eXn/nZ3/wMD/5+f/urr/jY3/YWH/RUX/b2//m5v/yMj/9vb/8vL/v7//i4v/WFj/Jyf/Z2f//f3/ 6Oj/rq7/PDz/DQ3/h4f/wcH/t7f/d3f/OTn/ERH/k5P/T0//DAz/IyP/ZGT/qan/x8f/fX3/NTX/ TEz/lZX/4OD/xcX/eHj/Kir/Q0P/kJD/39//0dH/gID/SUn/7e3/7Oz/l5f/QUH/XFz/srL//Pz/ vLz/YmL/KCj/f3//kpL/MjL/UlL/cHD/MTH/j4//VFT/Fhb/dnb/29v/pqb/Pz//zMz/xMT/mJj/ Vlb/wsL/LCz/p6f/NDT/uLj/RET/cnL/W1v/FRX/ior/+vr/8PD/enr/np7/ISH/ycn/+/v/fn7/ sLD/tLT/ZWX/6en/8fH/Hx//oqL/YGD/dXX/ICD/qKj/Nzf/jIz/5OT/JCT/LS3/vr7/+fn/qqr/ Skr/4eH/o6P/U1P/2Nj/Xl7/PT3/nJz/1dX/lJT/zc3/xsb/8/P/q6v/6ur/n5//ysr/9/f/aWn/ mpr/MDD/w8P/bGz/paX/Ly//gYH/4uL/Fxf/HR3/jo7/EhL/GRn/hIT/2dn/R0f/Ozv/9PT/hob/ s7P/Pj7/c3P/fHz/S0v/a2v/hYX/iIj/MzP/lpb/Dg7/UFD/JSX/Wlr/pKT/Gxv/ra3/FBT/u7v/ Zmb/Hh7/Y2P/g4P/XV3/QED/SEj/r6//vb3/ExP/KSn/trb/oKD/rKz/QkKWJ6mTAAAAAWJLR0QA iAUdSAAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+YIEAgSCBSc5zgAABZoSURBVHja7Z1Z eBRVFscbAdmEBhQUScIeoNl7SVXLpggxCNgiGpYJKCD7OkjLHlBBVECJNhgaBiIgew9BFhccp0UF NW4Dwogyjo7IKDNujDPP0yGJSaer+p6quufeW9X1e/LB70vVn+pzz3bPcThsbGxsbGxsbGxsbGxs bGxsbFKSGtfUrFX72jp169Wp3+C6ho2cvJ/HojRu0vT6G5o1v/GmFje3TEtPT88oJ/afaa1at2nb rn2HzI6d6nd28X5QC9Cla7fuPXr2Ss9we7y+LEmSZX8CsixlZfm8HndG+i29+/Tt17+xrbwubr1t wO29W6W7Y0oryKxCTPyY9OmtBw7KviOH9xuYicF3Dhk6LM3tzZLAWldX3ufJSLvpruw6Ad7vYgLu Ht7hnpYxteFftiqluo9od+99tuzq5OSOHNXS7dP7bSuS5c0Y3W5IE1t2BRqM+V0eZbmryD523P2d eb+hWDwwfkKaNwtD7nJknzt94oOTbFemjMlTpqZ7UT7vah+7J33a9Bm26jNn9Uz3UTgrYUie9NnZ c1JZ9bm/n5fmZaZ3xbee9tD8IO8358TDC0Z4GNgTBdXdCxd1Sb1PvdHiJemY52VyZG/60qap9anX W9aLtUFJ/NTzlw/mrQMzcle09PHV+yqS55FHH0sJ+7JyFUeLEk/Mvox73PKBaXZ+BpcjUw1fRvPV lhb9iTZuziY8kayMJ5+yrOjZa8QTvEz0oXdYUvS168QUvEz0BfUtJ/rTz4hlw6vjy1h/t6W8l5kF aUILflX0Z58L8daJGhs25oniFiZD9i653yIR6fOFXt5qApHcK6xQLd00MUPYUzORrIzxZrcu4c15 whvxeLz5W0xtXf6wxMNbQs1I7q3bTOu7FC1raSKbUonvhe1h3trpY34bsxyb1ZE9O2414Yce2mnO T7wM34u7TGfRn9ptPiteFdndzGRt1HtGmMxRScQ3ob+JfPS9fTJ4C0YBKWOPaU7RrvtEqLNRwFMw h7eWMDLzTHxuxpO1/4AJjEtReysYlQqkjDHCG5fHd5vVGVfB0z3CW9Pk/LGV6T2V6vgOFoscFg1p yVsgBLKGCewtNkvnLQ8KcvohQUPRRi+5eYuDhTtTyCz64bYW8caV8K4X8BCdvM8M9U3d+I4Ip3nX 0ZaJf1Q0n9iIt8bxrB3BWxJ0so4KdbvrWB5vQRgg5b8sjuavWNEdV9B84auiOOjLU0PxmINeKEi7 6IA03lKw07z1ZBE0TyHFSzV/jb/my1NJ8VLNN/HWPEVOziqaj63HV/NjqaZ4TPOph3n6imtTwR9P 0HzCXn6ad7V+zKmENO84L8Unj+b98rw0X8qprejwPotnstTJGsclr9ioraWztcnxbeRRs3jJwhUJ Mt5u7Jstmlm26gbD8zrreugQa1aWNTCacRj6x9QLgRLI/xNLxR9vxft9BUBawdBVLNptuZ4sPWT1 Zee2tLdY36FevG+wclsyrdRba4i8P7M5QrumYi5LhVFRForvTd04PxHpTRaRf5+Ujjqr4zuBb873 2IY8jry62Ob8qdRMkSdhGrJ3HrI98upkIY8T2WnuO8soeHMxE1zz7dSKAm9F8RQvamP7hwpIiP3+ y+xAXxFfVyzT8gfbrKiQH8VRPLzENisqSEil0M22t6KK922MgGiTnc1KwkCMgGiiHQQlIesV+rmW 5+3cSlIeaUi7UXFDoX12JkV+h7ZzvtF2yQn4KA9XmGmfnURm0z1BC1K4/xBK1kmaMejTqXUhSCen ohQlf8Z2EAFI0+k5imttBxFE6w3UJF9nf+Qg5HdppVqyU7yvGU6vxpTioTV2FASFUpfiE/ZHDibt PSqfuV190wCVz9y25FoYQcOa27UgTSwy/pmvtH1yTbxfZFjyVVbyyUvuKXjooVVHMV9J/sBoCJpr nZtvJY9eW8MZiUSczsPZ8/BUXxg1KPkK1BTihwUP3vZqvacX92HwD7uzKFRxsrnCXbLRuuTl7cYS ivUwW1c+2jM4Eg4EAsGwc9vHyD0yJfXiijau8CebsYou84zlzZch9u8/ubfycHeF6v8F8/d0unN1 380VWoSkuWRomUKjXngqdD8ep4LLeQTvn/e0UooPTfP2Rqqgi/EqnuPOVP9jkR1Ymp9WTqrWQioD +IxMasULgyYUJf61yAIczVUUd7i6IJ3an+oPhx7GcyTOKtm7yDgMzUs6q72fC8m0jM3RLfkCtBPt L8oPFbmLvuYlSYZg18L5puSzev3EuXhXsY6pPFPkTdqaJ1Pc4RqIY80L9B6gv8drtVU9YCLv0NW8 JPkH9y6OZfHp7ZbDi4pLaqj+0Uh3mpqXnE1+kt2JlJnWuXNrJl7zyrqo+p+N0Ay/mhJ8hxlIDsK5 qEMPs/Cc8rbJnijyV2qabyF5ay6kz0qqoysC7YlXm0je1xTZSUlzouIOB9YveZYe13wyYnZvdHLH NTSFyg8MoHgx1ku+FSX+7USmIGa0ZMKsgVBfCpoDFHecxHLKJD3z5aZi1jzvJcgR+syw5hDFHYfQ /GAdN/sfQK0aTI0S/nzIaDp7DCgaOY/mB+u4Cjoe9doEuUk1ZCxKGQN642K8woikfZj/BNwycx5x RU/oYwOa74Qllj5HPK8ytUZDDbCb+NcRY+LQF7o1H3oG9JLFmNdxNNfjxmBfx5JO1yRqfkHnQwwF TtHvgflLztK6PuF36JeDpFHEncih53RpfroI+I64n5XGSn8Ogxtw0k3XETUfoMOJOw28y/AO8kyC HdoyuLksej+lvz1G0jy8XLMuUMUzsXtnXtBWGxrJZBiilP8JUfNuGjWHKr4SfbiM3ESTmziKTSei dKo2UfMxmjQHK87Acg7Q4ibezWq+kLS/PlHzLzVorl5ajmf13xm83Fda3MThzNr45anXkn5+4evB mpcA9yuvRmyJquRZLca8A7u5tvJCYjY/nA3UXCzF/bKWbOI9DJvK5a+JsX+4H0jzEmAvAyPF/X4N i3AGMx0VJ48lHu3hNwCak0rLzBX3/wPumWPVvdU0f5G4Ejm8naw5qbRcTjG71SUvwo35EMbzbuQS 4nre8DckzUEliZjioxm+1wxwmmUo6+kr8kXi0Lvg2uQ/PQEV9/u/BadZhjG/dyjfTByTFNyVTHOo 4mzn4IGrcbdyGHgjP0K8exB8XV1zsOJsTeZ5aDB0G4/7zPKlfxI1/1btwYCKO3owPqRugZ6fA/hM i/vwNpLlC25RvvcLKy2jJ8gTkaFTFG7nNLusF3HcffCsUsQAKy1zUNzvhw7w783rQvMI4hzwoMKG bmBpmYfifuhVZ35rDtPmEzXPrf50wNIyF8X938FMHtalJQjuLUTNO30YrziwtMxFcf85mMvSlecA Fjcxegg+fKnK/w8tLWMXOlX4CGb0unEdb+sm7kQOHHjkt1ANXFrm9BnJsIk4VG+O6NB8F1Hz+z6S tSmOXlpWZTIoZd6D83xb90qi5m9flLUojl9aVuUbkJeIeHsCqDnxOQOvfS+LVVpW42NQXMwsg6+K pyPJnQ1sKpSFKi2rAfISGwswZ8hDXIocaHIUqjjXTwg01LyJCPPLPP2ImkfNoLi/J+QxmwoxF9GT TWcwNWfF/Zcgjjm8awRX8ydoaM5bcb8MOeJvEGTRh+dL45ozLC2rQey6jNFMlHXYHp0X4asoPpr3 O/j9qwGxUHNhNn14NLTeiKq4vykgFrpRnPGfnuVGNGdcWlaB6HrFuEmEBy3Hs0e/5qxLyypAOp5b 8I73q+J9TvdkMNalZRU2A17gZpEk93tJV8/V4FOSSGQQ4PkF2xbs/UKX5qIo7r9MTrLUEG1Rlvdf OjQXRnF/H7Lk1wiQ1YrHq31bjziK+9uR81o1hZPc74UcQYIq7r+RLDnStEZDeD/TpDmn0rIyq8iS 1xYhd1sdr5bdMbxKy8qMihIf+FoRJfd7p4A151daVgQwCqeOkJL7vQ8C41COpWVFAJLXFVPy08Cm LJ6lZUUAktcTUnJTlJYVMathgd6hdRwRJvNcAUDy+gJKDlbc4WR+s4wEQPIG4kkOvbV8VXPRdqkn He9bxnVi+Vh++K3lcs3/LZbmP5BDoYbCSQ68tVwB3uIWXfxIlnyOaJJDb7tVan67SJoDhrI4BUve alYca3GLTiBrKfi3flRFh+I4i1v0AukDbS1SIU6X4hiLW3TTAfAGIm3H1qk4/cUt+rkB8AptxWmq gN5aVtKc8/Wb31gMSMe1E8avhd5aVtYcc2+mBiA3V9qLIjn01rKa5uuF0DwX0JPIcHpcUqC3ltU1 p7W4xRCQkaCZYtRqoXdokxAaKcCrEMdXx+goRLEWetstueY0FrcYwwv5rXYSoVpLRXEqi1sMUgI5 kERImEMVJzpghhe3GGV3FPAanfnntcB3aA/OJQ6dR1ovCWUp5BKi6xbe4SdU8dV/l+ZtI2r+L66a LwPFz9yGDpUDLi338vulc8RREAYWt1AANnaoD99YSNt4Zuke4mKT0L0cNf8JVEPk61lpHYiNt7iF CnVA81iO8XTMtQ/ElnYTJ8uG9vDSXIJ1PPXn6JjrGYgtrauFsLiFDl9HQa/Dc1SFroHY0pIu1Be3 UOIgbJyZi19dSOd4ZulUA8qLW2ixHlhjGcjLS9Q9EFvq+TLVxS3UgM7bGMQp6WlgILbUhry45QkO msOmmTkc0AUnvBRXcj/khTOpLW6hSBFMcccdXBJbBgdiyy3qUlrcQhHivuQKcnh0DxkeiE1rcQtN IH1DZXCYnEBhILZcSFydFGZcf4FPfWDf7DS0hmHFY5p/T2FxC1V+Bq/PYn7OUBrPLLcmzvghLW6h C7y2xfryCrXxzPJH9xlb3EKXU1Gw5IERxv+cFsXpjWeW37/TyOIWynyuob+PaccW1YHY8rMP61/c QptDGhZms0zqgwudQNf1F92LW6hDzHBW4T52xpz+eOZWTxM1n88k8riipcMvMFo0xbXcoR3xHz2L W+gDGDhUBVbGXEtpGU5LYmkpmMtgMmtHDaac2fpJrM2/GZoXt2AAXz1ZCpuJ2iWwYqyOYcHu4UTN //kL8tut09asHRjLoDJUchaWgtAznlnb4hYUdmq8dcPgHh/urmX3/fDFLTh00rCfvJT78cMF5F3L 7rXkxS2YZ+gvWq8k4DeDom/+dZ8kar4S8cMaqvlu2URkNxGq+Cn9P373dtJZEdyKZz9/1eQilvIg bgIXXOg0oonnV5Lmn6D9mKVtWhV3TEK1LKdhJQmjA7GJi1tcT2L9mIFNQ3EP8wxmNQ4YAhkeiO05 RtAcrQKtZ5PGdETLAtwyQWGQquf65H8KbSxkTU2hZxkzEC1LESvFiYtbwkg5RR12JWZZZqP5LDD/ idKwYM8rSTVHkvyEro0OeEVnkJ2jNp45+eIWHMkz5upR3DEHLY9fH2DnKI5n9kxX17wYx3w21zdj w/VvrDgBEApTHc/sUV97chYn/iQmMtk+jt9Pdsopj2f2DFGLvHDGWRTqHfkQXIiTaSNfsqY+ntl7 QVnz0PcorzhS92qeRTi1IfkwwZYjjGf2fqqoAtIdImDpRYEuSK55p+SWDmU8s1dpi3JtnEHQP0b1 Ku5wLcVxzccn/d0h7ehUWNwSQUpqaGkZqg7Shtukc2DRtqImLG4Jv4RjOAuBTa2KBPNRPgMpSUMV 4h5a7+W4ppnHjyI5wYt0H56lIB0vO1QjBdTNv759lXFvzc/ykFKl8iTdh2cpg3Gq4D61zxx517Ls ubjgxM91X/3PgKV5aPWgBfqnO17lURxz97Wya46/a1n2edwZGW4vYpmReJWDwGM4fqI8UOkEFWLz r1HaRY0p7nAhNbRICj+/1WINldbJWYMfeexcRyqb+IZVX5eeKdpKIF38ALxoloQA1sZyucWsqi3v xT1EGBZonF0GwqDffu5Y1UHZ22JceddD8YU1aeIMlDZCb6NjY69+5mh9B6XuQ1rasCst09K9vKfW 0cJIrF/JU7iNz7JV1C5lN42PPPaZC7eRSlyIbZBA+EyuMCPnjLsr5Z/5AhEGgZsBvSXPRESYPGwG zhvbKxD3mYuxY0B4DhgOPCu5+1kruRVYtI/SU9zh4jjC1DR4axvKk1cntMQawSEmGw3myavD4LqW ydk3mK7ijuAKOx5KzjFqDmIFQq7RFoiDtKKgSgLj7RM0GQ9QdBArCOE0WFiEkXpuTRDZYp+gqqyh fXaWgXkv1ez8l/rZWca2F+wYVJmtURzFHa7tQqyPE4/Ca5AUdzjCYq24F4aVSGallFtftE1LIlvp 5WwTcTGcXmoartDZ2qhGsJltWqrTFNGslOKcYAdE8UxBCYKq0t/OtcTxPyNXJmAE9tieYhVazaRa l1AmXGCncSvpZ+iSCpQ5+21zXsEgTP+wCgdsc17ONHxDXkaAz7418bhIXE1HjXB32zsvZbiuOTf6 iBy0j1C//wK6R16V4mF2suUu+tXOZLj6c9zDKgazWR2dFQQOpXiCa39Dxoo7HMHMlK74XyLuo0Mg lNLtuD8xdFYqiRxJXc1PUO4/BGuOPXBbWC5E+SjucDQ6mprZlr6MMitKTErJDq5mbB3yeFwvI831 E5nLdO526tb81cJU0/wh1iFQdQJ3tE4tzaELeDE1n5xSmvfB6ffUqPlrKaT5UBEUj2m+icV2MyHg bsd/07ze1NTQ/LIoisc0P5wSDUXN+HqH8bj2zrO+5n15RkAKmh9HmkgsDhc4RvnKOBmsq+TJiShv hROJbLRwzeLST5yytckJdbNse8v+TVwqEmTCr4/mrQ0OsxtyqLrBCG6yZDL3LnHc8UQCf7LgdIUL YjmHCTj7WuwQvTicaU+WHkJvWGJacAXTGgh6cFYl/OdR1jHog44z6601QiD6pkWiolb9hIs41Yic sIRx+d9MJrdS6BCuO838nssUcxiVCgJOsw8putJUeE+lOqHct8x8im7dgHxnGYNg1Lx9ooUrTXNu xhPpatL4f+s1JvzEywhGzZjQXfPfKG/hjBB6e6DZXJeRg037iZcRcL6Cs2YOiYMPmM5RSSTc8B3T HKP7jtUQNjOuBVek/2xTWBfvRrPblEoCzpOnxPddjrwsZH1TL8HodME7F88fiJoqvgcQ3vAu7vpU Q5wbnmMJI16NUOO+abylVWb3yRqWMeLxuELv9R3BW95Eeh86Y1HBy0RvvOh9sWz6D7usLHiZ6EUf CHSlq91Zq5qUOMLR7WL06coLXota8dBUIug80J57RFq4aFIkVQQvJRCZ9CnX6y4/HjoespofTsIV yjlbwOlTLxw5M5oKJjyRYKThmHPMrXpG8+FnUu4Dr8QVjtaZxbRGevDE3EhqfuCVBELRTePZFOyk g1/WdJqg340BMdXrZc5Dzu/+MvTXbc5w6hqUBAJh597tO9A2uqzb2elMytuTRFzBSE6TAV9RX+Z6 5XLHGTmhVPLANRH72HM2dfsHtSUjpz4/VCsnErTNSXIC4UjOjG/Hn7/FmO5TC/b8vCEasuUG4gqG nDnv5X7w3TntwktfH1yfPXlDNBK2jYlWXMFwxJnTePI3X3w3u+clovTekt1LB33wU53jUWfIVtsI rkAwFHFGczZ8srrp8wM2D7rcp92Nq0blX6XtDz9+VfBdhxsWf5PbpPhMTqnWtiGhSEz7cDgUikSc Tme0nNh/RiKhmNCBgC21jY2NjY2NjY2NWfk/LeU8Y36uR1QAAAAldEVYdGRhdGU6Y3JlYXRlADIw MjItMDgtMTZUMDg6MTg6MDcrMDA6MDCyp2KHAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIyLTA4LTE2 VDA4OjE4OjA3KzAwOjAww/raOwAAAABJRU5ErkJggg==";

    private String SCALE = "scale";

    private String VERSION_TAG = "DocRevision";

    private String TIME_TAG = "Datetime";

    private String LEFT_TAG = "<";

    private String RIGHT_TAG = ">";

    private String MCP_SERVICE_URL;

    private String MCP_ALERTS_URL;

    public NetworkState processSvgFile(List<PMConfigurationDTO> lstPMConfigurationDTO,
                                              List<AlertsStatusResponse> lstServiceStatus,
                                              List<MCPAlarmsResponse> lstMCPAlarmsResponses,
                                              NetworkState networkState,
                                              boolean withDetails) throws IOException, TranscoderException {

        MCP_SERVICE_URL = env.getProperty("mcp.ip") + "/ui/#/transport-services?keyword=";
        MCP_ALERTS_URL = env.getProperty("mcp.ip") + "/alarms/#/?alarmFacets=%7B%22dynamicFacets%22%3A%7B%22keytext%22%3A%22<port-details>%22%2C%22severity%22%3A%5B%22INDETERMINATE%22%2C%22CRITICAL%22%2C%22MAJOR%22%2C%22MINOR%22%2C%22WARNING%22%5D%7D%2C%22staticFacets%22%3A%7B%22deviceName%22%3A%5B%7B%22label%22%3A%22<device-label>%22%2C%22value%22%3A%22<device-label>%22%7D%5D%7D%7D&keyword=<port-details>";

        String image = networkState.getNetworkimage();
        String uri = "temp.svg";
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Path path = Paths.get(uri);
        Files.writeString(path, image);
        Document doc = f.createDocument(uri);
        searchAndUpdateElement(lstPMConfigurationDTO, lstServiceStatus, lstMCPAlarmsResponses, doc, withDetails);

        byte[] svgArr = transcodeToSVG(doc);
        if (svgArr != null) {
            String str = new String(svgArr);
            if (str.contains(SVG_TAG))
                image = str.substring(str.indexOf(SVG_TAG))
                    .replace(RIGHT_TAG + VERSION_TAG + LEFT_TAG, RIGHT_TAG + VERSION_TAG + networkState.getId() + LEFT_TAG)
                    .replace(RIGHT_TAG + TIME_TAG + LEFT_TAG, RIGHT_TAG + DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.systemDefault()).format(Instant.now()) + LEFT_TAG);
        }

        log.info("Converted SVG image successfully.");

        networkState.setNetworkimage(image);
        return networkState;
    }

    private void searchAndUpdateElement(List<PMConfigurationDTO> lstPMConfigurationDTO,
                                        List<AlertsStatusResponse> lstServiceStatus,
                                        List<MCPAlarmsResponse> lstMCPAlarmsResponses,
                                        Document doc,
                                        boolean withDetails) {

        log.info("Converting SVG image in progress.");
        NodeList nlList = doc.getElementsByTagName(SEARCH_TAG);
        for (int nodeLindx = 0; nodeLindx < nlList.getLength(); nodeLindx++) {
            Element eElement = (Element) nlList.item(nodeLindx);
            NamedNodeMap namedNodeMap = eElement.getAttributes();
            for (int nodeMapIdx = 0; nodeMapIdx < namedNodeMap.getLength(); nodeMapIdx++) {
                if (namedNodeMap.item(nodeMapIdx).getNodeValue().toUpperCase().contains(PREFIX_TAG)) {
                    String labelName = namedNodeMap.item(nodeMapIdx).getNodeValue().toUpperCase().replace(PREFIX_TAG, "").replace(SUFFIX_TAG, "");
                    if (labelName.contains(ALARM_TAG)) {
                        updateAlertsDetails(labelName, eElement, doc, lstMCPAlarmsResponses);
                    } else {
                        updateServiceDetails(lstPMConfigurationDTO, labelName, eElement, doc, lstServiceStatus, withDetails);
                    }
                }
            }
        }

        Element script = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "script");
        script.setAttributeNS(null, "type", "text/javascript");
        Text scriptText = doc.createTextNode(
            "function showAlert(alarms, href) {\n" +
                " alert(alarms);\n" +
                "  if (window.confirm('If you click OK we will take you to MCP Alarms page . Cancel will close this dialog ')) {\n" +
                "    window.open(href, '_blank');\n" +
                "  }\n" +
                "}"
        );
        script.appendChild(scriptText);
        Element root = doc.getDocumentElement();
        root.appendChild(script);
    }

    private void updateAlertsDetails(String labelName, Element eElement, Document doc, List<MCPAlarmsResponse> mcpAlarmsResponses) {
        String updatedLabel =  labelName.replaceAll(ALARM_TAG, "");
        MCPAlarmsResponse mcpAlarmsResponse = mcpAlarmsResponses.stream()
            .filter(dto -> updatedLabel.equalsIgnoreCase(dto.getHawaikiDeviceLabel()))
            .findAny()
            .orElse(null);

        if (mcpAlarmsResponse != null) {
            log.debug("Success Alarm exist {}", mcpAlarmsResponse.getHawaikiDeviceLabel());
            // Go to the parent node and find the rect element
            NodeList parentNodeList = eElement.getParentNode().getParentNode().getChildNodes();
            for (int prntNdLstIdx = 0; prntNdLstIdx < parentNodeList.getLength(); prntNdLstIdx++) {
                if (RECT_TAG.equalsIgnoreCase(parentNodeList.item(prntNdLstIdx).getNodeName())) {
                    log.debug("Sucess parentNodeList.item(prntNdLstIdx).getNodeName().toUpperCase() {}", parentNodeList.item(prntNdLstIdx).getNodeName().toUpperCase());
                    if (parentNodeList.item(prntNdLstIdx) instanceof Element)
                        updateNodeForAlerts(doc, (Element) parentNodeList.item(prntNdLstIdx), mcpAlarmsResponse);
                }
            }
        }
    }


    private void updateServiceDetails(List<PMConfigurationDTO> configurationDTOS, String labelName, Element eElement, Document doc, List<AlertsStatusResponse> serviceStatusList, boolean withDetails) {
        PMConfigurationDTO configurationDTO = configurationDTOS.stream()
            .filter(dto -> labelName.equalsIgnoreCase(dto.getLabelName()))
            .findAny()
            .orElse(null);

        if (configurationDTO != null) {
            log.debug("Success configurationList.get(0) {}", configurationDTO.getLabelName());
            // Go to the parent node and find the rect element
            NodeList parentNodeList = eElement.getParentNode().getParentNode().getChildNodes();
            for (int prntNdLstIdx = 0; prntNdLstIdx < parentNodeList.getLength(); prntNdLstIdx++) {
                if (RECT_TAG.toUpperCase().equals(parentNodeList.item(prntNdLstIdx).getNodeName().toUpperCase())) {
                    log.debug("Sucess parentNodeList.item(prntNdLstIdx).getNodeName().toUpperCase() {}", parentNodeList.item(prntNdLstIdx).getNodeName().toUpperCase());
                    if (parentNodeList.item(prntNdLstIdx) instanceof Element)
                        updateNodeForService(doc, (Element) parentNodeList.item(prntNdLstIdx), configurationDTO, serviceStatusList, withDetails);
                }
            }
        }
    }

    private void updateNodeForAlerts(Document doc, Element element, MCPAlarmsResponse mcpAlarmsResponse) {
        log.debug("Sucess,Adding alert text   {}", mcpAlarmsResponse.getHawaikiDeviceLabel());
        log.debug("Element name  {}", element.getTextContent());
        if (mcpAlarmsResponse.getState() != null && mcpAlarmsResponse.getState().equalsIgnoreCase("ACTIVE")) {


            StringBuilder alertData = new StringBuilder();
            alertData.append("Alarm Id: ").append(mcpAlarmsResponse.getId()).append("\\t")
                .append("Serverity: ").append(mcpAlarmsResponse.getConditionSeverity()).append("\\t")
                .append("Alarm last Raised Time: ").append(mcpAlarmsResponse.getLastRaiseTime()).append("\t ");
            StringBuilder newAlertsUrl = new StringBuilder(MCP_ALERTS_URL);
            int deviceLabelIndex = newAlertsUrl.indexOf("<device-label>");
            while (deviceLabelIndex != -1) {
                newAlertsUrl.replace(deviceLabelIndex, deviceLabelIndex + 14, mcpAlarmsResponse.getDeviceName());
                deviceLabelIndex = newAlertsUrl.indexOf("<device-label>");
            }

            int portDetailsIndex = newAlertsUrl.indexOf("<port-details>");
            while (portDetailsIndex != -1) {
                newAlertsUrl.replace(portDetailsIndex, portDetailsIndex + 14, mcpAlarmsResponse.getPortDetails());
                portDetailsIndex = newAlertsUrl.indexOf("<port-details>");
            }


            String alertArgs  = "'"+alertData.toString()+"', '"+newAlertsUrl.toString()+"'";
            String onmouseenterValue = "showAlert(" + alertArgs + ")";
            if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("CRITICAL"))
                element.setAttribute("style", "fill:red;font-size:5px;");
            else if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("MAJOR"))
                element.setAttribute("style", "fill:orange;font-size:5px;");
            else if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("MINOR"))
                element.setAttribute("style", "fill:yellow;font-size:5px;");
            else if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("WARNING"))
                element.setAttribute("style", "fill:blue;font-size:5px;");
            else if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("INDETERMINATE"))
                element.setAttribute("style", "fill:purple;font-size:5px;");
            else if(mcpAlarmsResponse.getConditionSeverity().equalsIgnoreCase("CLEARED"))
                element.setAttribute("style", "fill:green;font-size:5px;");
            else
                element.setAttribute("style", "fill:red;font-size:5px;");
            if(!("ACKNOWLEDGED".equalsIgnoreCase(mcpAlarmsResponse.getAcknowledgeState()))){
                Element animateElement = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "animate");
                animateElement.setAttribute("attributeName", "visibility");
                animateElement.setAttribute("from", "visible");
                animateElement.setAttribute("to", "hidden");
                animateElement.setAttribute("dur", "1s");
                animateElement.setAttribute("repeatCount", "indefinite");
                element.getParentNode().appendChild(animateElement);
            }
            Element parentElement = (Element) element.getParentNode();
            parentElement.setAttributeNS(null, "onmouseenter", onmouseenterValue);

        }
    }

    private void updateNodeForService(Document doc, Element element, PMConfigurationDTO configurationDTO, List<AlertsStatusResponse> serviceStatusList, boolean withDetails) {
        log.debug("Success,Adding text node with service  {}", configurationDTO.getServiceId());
        log.debug("Element name  {}", element.getTextContent());
        if (configurationDTO.getCustomer() != null) {
            Element textElement = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");

            textElement.setAttribute("y", String.valueOf(Float.parseFloat(element.getAttribute("y")) + 3.5));
            AlertsStatusResponse servicestatus = serviceStatusList.stream()
                .filter(alertsStatusResponse -> configurationDTO.getServiceId().equals(alertsStatusResponse.getServiceName()))
                .findAny()
                .orElse(null);
            if (servicestatus == null || servicestatus.getOperationState() == null ||
                servicestatus.getOperationState().equals("UP") || servicestatus.getOperationState().equals("UNKNOWN")) {
                Node parentNode = element.getParentNode();
                if (rightHandNode(element)) {
                    textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) - 80));
                    textElement.setAttribute("transform", "scale(-1,1)");
                } else {
                    textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) + 1));
                }
                textElement.setAttribute("style", "fill:black;font-size:4px;");
            } else if (servicestatus.getOperationState().equals("DOWN")) {
                if (configurationDTO.getMasked() != null && configurationDTO.getMasked() == 1) {
                    if (rightHandNode(element)) {
                        textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) - 80));
                        textElement.setAttribute("transform", "scale(-1,1)");
                    } else {
                        textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) + 1));
                    }
                    textElement.setAttribute("style", "fill:orange;font-size:4px;");
                } else {
                    Element imageElement = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "image");
                    if (rightHandNode(element)) {
                        imageElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) - 84));
                        imageElement.setAttribute("transform", "scale(-1,1)");
                        textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) - 80));
                        textElement.setAttribute("transform", "scale(-1,1)");
                    } else {
                        textElement.setAttribute("x", String.valueOf(Float.parseFloat(element.getAttribute("x")) + 5));
                    }
                    textElement.setAttribute("style", "fill:red;font-size:4px;");
                    imageElement.setAttribute("y", String.valueOf(Float.valueOf(element.getAttribute("y")).floatValue()));
                    imageElement.setAttribute("width", String.valueOf(3.75));
                    imageElement.setAttribute("href", ERRORIMAGE);
                    Element animateElement = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "animate");
                    animateElement.setAttribute("attributeName", "visibility");
                    animateElement.setAttribute("from", "visible");
                    animateElement.setAttribute("to", "hidden");
                    animateElement.setAttribute("dur", "1s");
                    animateElement.setAttribute("repeatCount", "indefinite");
                    element.getParentNode().appendChild(imageElement);
                    element.getParentNode().appendChild(animateElement);
                }
            }
            if (!withDetails) {
                textElement.setTextContent(configurationDTO.getServiceId());
            } else {
                textElement.setTextContent(configurationDTO.getCustomer() + "/" + configurationDTO.getServiceId());
            }
            Element hrefEle = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "a");
            hrefEle.setAttribute("target", "_blank");
            XLinkSupport.setXLinkHref(hrefEle, MCP_SERVICE_URL + configurationDTO.getServiceId());
            hrefEle.appendChild(textElement);
            element.getParentNode().appendChild(hrefEle);
        }
    }

    private boolean rightHandNode(Element element) {
        return element != null && element.getParentNode() != null && element.getParentNode().getAttributes() != null
            && element.getParentNode().getAttributes().getNamedItem("transform") != null && element.getParentNode().getAttributes().getNamedItem("transform").getNodeValue() != null
            && element.getParentNode().getAttributes().getNamedItem("transform").getNodeValue().contains(SCALE);
    }

    private byte[] transcodeToSVG(Document doc) throws TranscoderException {
        log.debug("Transcoding image to svg ");
        try {
            //Determine output type:
            SVGTranscoder t = new SVGTranscoder();
            //Set transcoder input/output
            TranscoderInput input = new TranscoderInput(doc);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            OutputStreamWriter ostream = new OutputStreamWriter(bytestream);
            TranscoderOutput output = new TranscoderOutput(ostream);
            //Perform transcoding
            t.transcode(input, output);
            ostream.flush();
            ostream.close();

            return bytestream.toByteArray();

        } catch (IOException e) {
            log.error("Error transcoding image  {}", e.getMessage());
        }
        return null;
    }

}
