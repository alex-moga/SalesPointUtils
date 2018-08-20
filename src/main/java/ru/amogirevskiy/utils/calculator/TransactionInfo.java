package ru.amogirevskiy.utils.calculator;

import java.time.LocalDate;

class TransactionInfo {
    LocalDate transDate;
    float transSum;
    int office;

    public LocalDate getTransDate() {
        return transDate;
    }

    public void setTransDate(LocalDate transDate) {
        this.transDate = transDate;
    }

    public float getTransSum() {
        return transSum;
    }

    public void setTransSum(float transSum) {
        this.transSum = transSum;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }
}