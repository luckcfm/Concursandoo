package com.example.luckcfm.concursando;

import java.io.Serializable;

/**
 * Created by luckcfm on 19/08/14.
 */
public class Concursos implements Serializable {
    private long id;
    private String nomeConcurso;
    private String instituicaoConcurso;
    private String dataConcurso;
    private String siteConcurso;
    private String cargoConcurso;
    private String avisarEm;

    public String getAvisarEm() {
        return avisarEm;
    }

    public void setAvisarEm(String avisarEm) {
        this.avisarEm = avisarEm;
    }

    public String toString(){
        return this.nomeConcurso;
    }

    public String getCargoConcurso() {
        return cargoConcurso;
    }

    public void setCargoConcurso(String cargoConcurso) {
        this.cargoConcurso = cargoConcurso;
    }

    public String getNomeConcurso() {
        return nomeConcurso;
    }

    public void setNomeConcurso(String nomeConcurso) {
        this.nomeConcurso = nomeConcurso;
    }

    public String getInstituicaoConcurso() {
        return instituicaoConcurso;
    }

    public void setInstituicaoConcurso(String instituicaoConcurso) {
        this.instituicaoConcurso = instituicaoConcurso;
    }

    public String getDataConcurso() {
        return dataConcurso;
    }

    public void setDataConcurso(String dataConcurso) {
        this.dataConcurso = dataConcurso;
    }

    public String getSiteConcurso() {
        return siteConcurso;
    }

    public void setSiteConcurso(String siteConcurso) {
        this.siteConcurso = siteConcurso;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }


}
