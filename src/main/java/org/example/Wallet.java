package org.example;

import java.util.ArrayList;

public class Wallet {
    String owner;
    ArrayList<String> listKartu;
    ArrayList<Integer> listUangKoin;
    ArrayList<Integer> listUangLembaran;

    public Wallet(String owner) {
        this.owner = owner;
        listKartu = new ArrayList<>();
        listUangKoin = new ArrayList<>();
        listUangLembaran = new ArrayList<>();
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void tambahKartu(String namaKartu) {
        this.listKartu.add(namaKartu);
    }

    public String ambilKartu(String namaKartu) {
        boolean isDeleted = this.listKartu.remove(namaKartu);
        if(isDeleted) {
            return namaKartu;
        }
        return null;
    }

    public void tambahUangRupiah(int jumlahUang) {
        if (jumlahUang < 0) {
            throw new Error("jumlahUang tidak boleh minus");
        }
        if (jumlahUang > 1000) {
            listUangLembaran.add(jumlahUang);
        }  else {
            listUangKoin.add(jumlahUang);
        }
    }

    public int ambilUang(int jumlahUang) {
        boolean isUangLembaranTaken = this.listUangLembaran.remove(Integer.valueOf(jumlahUang));

        if (isUangLembaranTaken) {
            return jumlahUang;
        }

        boolean isUangKoinTaken = this.listUangKoin.remove(Integer.valueOf(jumlahUang));

        if (isUangKoinTaken) {
            return jumlahUang;
        }
        return 0;
    }

    public int tampilkanUang() {
        int totalUang = 0;
        for (Integer uang : listUangKoin) {
            totalUang += uang;
        }
        for (Integer uang : listUangLembaran) {
            totalUang += uang;
        }
        return totalUang;
    }
}
