package com.gooseeker.fss.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.json.JSONArray;

public class Annotate
{
    private Long id;
    private long docId;
    private String product;
    private String factor;

    private String max;
    private String min;
    private String unit;
    private String adi;

    private String adiWeb;
    private String cns;
    private String ins;
    private String cas;
    private String struc;

    private String mole;
    private String property;
    private String toxico;

    private String biological;
    private String funct;
    private String disease;
    private String proremark;
    private String facremark;
    private Timestamp createDate;
    private String user;
    private int page;
    private String testsId;
    private String prostd;

    private List<AnnotateXpath> annotateXpaths;
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    
    public long getDocId()
    {
        return docId;
    }

    public void setDocId(long docId)
    {
        this.docId = docId;
    }

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public String getFactor()
    {
        return factor;
    }

    public void setFactor(String factor)
    {
        this.factor = factor;
    }

    public String getMax()
    {
        return max;
    }

    public void setMax(String max)
    {
        this.max = max;
    }

    public String getMin()
    {
        return min;
    }

    public void setMin(String min)
    {
        this.min = min;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getAdi()
    {
        return adi;
    }

    public void setAdi(String adi)
    {
        this.adi = adi;
    }

    public String getAdiWeb()
    {
        return adiWeb;
    }

    public void setAdiWeb(String adiWeb)
    {
        this.adiWeb = adiWeb;
    }

    public String getCns()
    {
        return cns;
    }

    public void setCns(String cns)
    {
        this.cns = cns;
    }

    public String getIns()
    {
        return ins;
    }

    public void setIns(String ins)
    {
        this.ins = ins;
    }

    public String getCas()
    {
        return cas;
    }

    public void setCas(String cas)
    {
        this.cas = cas;
    }

    public String getStruc()
    {
        return struc;
    }

    public void setStruc(String struc)
    {
        this.struc = struc;
    }

    public String getMole()
    {
        return mole;
    }

    public void setMole(String mole)
    {
        this.mole = mole;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getToxico()
    {
        return toxico;
    }

    public void setToxico(String toxico)
    {
        this.toxico = toxico;
    }

    public String getBiological()
    {
        return biological;
    }

    public void setBiological(String biological)
    {
        this.biological = biological;
    }

    public String getFunct()
    {
        return funct;
    }

    public void setFunct(String funct)
    {
        this.funct = funct;
    }

    public String getDisease()
    {
        return disease;
    }

    public void setDisease(String disease)
    {
        this.disease = disease;
    }

    public String getProremark()
    {
        return proremark;
    }

    public void setProremark(String proremark)
    {
        this.proremark = proremark;
    }

    public String getFacremark()
    {
        return facremark;
    }

    public void setFacremark(String facremark)
    {
        this.facremark = facremark;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public String getTestsId()
    {
        return testsId;
    }

    public void setTestsId(String testsId)
    {
        this.testsId = testsId;
    }

    public String getProstd()
    {
        return prostd;
    }

    public void setProstd(String prostd)
    {
        this.prostd = prostd;
    }

    public List<AnnotateXpath> getAnnotateXpaths()
    {
        return annotateXpaths;
    }

    public void setAnnotateXpaths(List<AnnotateXpath> annotateXpaths)
    {
        this.annotateXpaths = annotateXpaths;
    }

    @Override
    public String toString()
    {
        return "Annotate [id=" + id + ", docId=" + docId + ", product="
                + product + ", factor=" + factor + ", max=" + max + ", min="
                + min + ", unit=" + unit + ", adi=" + adi + ", adiWeb="
                + adiWeb + ", cns=" + cns + ", ins=" + ins + ", cas=" + cas
                + ", struc=" + struc + ", mole=" + mole + ", property="
                + property + ", toxico=" + toxico + ", biological="
                + biological + ", funct=" + funct + ", disease=" + disease
                + ", proremark=" + proremark + ", facremark=" + facremark
                + ", createDate=" + createDate + ", user=" + user + ", page="
                + page + ", testsId=" + testsId + ", prostd=" + prostd + "]";
    }

}
