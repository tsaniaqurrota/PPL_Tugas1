package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void testSetOwner() {
        //Mengganti pemilik wallet
        Wallet wallet = new Wallet("Mingyu");
        wallet.setOwner("Deka");
        assertEquals("Deka", wallet.owner, "Owner harus diubah menjadi Deka");
    }

    @Test
    void testTambahKartu() {
        Wallet wallet = new Wallet("Joshua");

        //1. Kartu ditambahkan pada list kartu
        wallet.tambahKartu("KAI Access");
        assertTrue(wallet.listKartu.contains("KAI Access"));

        //2. Kartu ditambahkan tidak pada list kartu
        wallet.tambahKartu("Debit Card");
        assertFalse(wallet.listUangKoin.contains("Debit Card"));
        assertFalse(wallet.listUangLembaran.contains("Debit Card"));
    }

    @Test
    void testAmbilKartu() {
        Wallet wallet = new Wallet("Scoups");

        wallet.tambahKartu("Mastercard");
        wallet.tambahKartu("VISA");
        wallet.tambahKartu("BCA");
        wallet.tambahKartu("NPWP");

        //1. Ambil kartu yang berada di list
        String kartuDiambil = wallet.ambilKartu("Mastercard");
        assertEquals("Mastercard", kartuDiambil,"Kartu yang ada pada list kartu dapat diambil");

        //2. Kartu yang sudah diambil tidak lagi berada di wallet
        assertNull(wallet.ambilKartu("Mastercard"), "Kartu tidak dapat diambil karena tidak ada dalam list kartu");

        //3. Kartu selain yang sudah diambil masih ada dalam list
        assertTrue(wallet.listKartu.contains("VISA"),"Kartu VISA masih ada di dalam wallet");
        assertTrue(wallet.listKartu.contains("BCA"),"Kartu BCA masih ada di dalam wallet");
        assertTrue(wallet.listKartu.contains("NPWP"),"Kartu NPWP masih ada di dalam wallet");
    }

    @Test
    void testTambahUangRupiah() {
        Wallet wallet = new Wallet("Jeonghan");

        //1. Uang koin yang ditambahkan masuk ke list uang koin
        wallet.tambahUangRupiah(100);
        wallet.tambahUangRupiah(200);
        wallet.tambahUangRupiah(500);

        assertTrue(wallet.listUangKoin.contains(100), "100 harus dimasukkan pada list uang koin");
        assertTrue(wallet.listUangKoin.contains(200), "200 harus dimasukkan pada list uang koin");
        assertTrue(wallet.listUangKoin.contains(500), "500 harus dimasukkan pada list uang koin");
        assertFalse(wallet.listUangLembaran.contains(100), "500 harus dimasukkan pada list uang koin");
        assertFalse(wallet.listUangLembaran.contains(200), "700 harus dimasukkan pada list uang koin");
        assertFalse(wallet.listUangLembaran.contains(500), "500 harus dimasukkan pada list uang koin");

        //2. Apakah uang lembaran yang ditambahkan masuk ke list uang lembaran
        wallet.tambahUangRupiah(2000);
        wallet.tambahUangRupiah(100000);

        assertTrue(wallet.listUangLembaran.contains(2000), "1000 harus dimasukkan pada list uang lembaran");
        assertTrue(wallet.listUangLembaran.contains(100000), "100000 harus dimasukkan pada list uang lembaran");
        assertFalse(wallet.listUangKoin.contains(5000), "5000 harus dimasukkan pada list uang lembaran");
        assertFalse(wallet.listUangKoin.contains(7000), "7000 harus dimasukkan pada list uang lembaran");

        //3. Error saat menambahkan uang kurang dari 0 atau minus
        assertThrows(Error.class, () -> wallet.tambahUangRupiah(-1000), "Error saat uang yang ditambahkan kurang dari 0");
    }


    @Test
    void testAmbilUang() {
        Wallet wallet = new Wallet("Jay");

        //1. Mengambil uang di list kosong
        int uangTaken = wallet.ambilUang(50000);
        assertEquals(0, uangTaken, "Tidak dapat mengambil uang dari list kosong");

        //2. Uang lembaran yang telah diambil harus hilang dari list uang lembaran
        wallet.tambahUangRupiah(100000);
        wallet.tambahUangRupiah(50000);
        int uangTaken2 = wallet.ambilUang(100000);
        assertEquals(100000,uangTaken2,"Uang 100000 diambil dari list uang lembaran");
        assertFalse(wallet.listUangLembaran.contains(100000), "Uang 100000 tidak ada dalam list uang lembaran");

        //3. Uang koin yang telah diambil harus hilang dari list uang koin
        wallet.tambahUangRupiah(500);
        int uangTaken3 = wallet.ambilUang(500);
        assertEquals(500,uangTaken3,"Uang 500 diambil dari list uang koin");
        assertFalse(wallet.listUangLembaran.contains(500), "Uang 500 tidak ada dalam list uang lembaran");

        //4. Ambil salah satu uang yang bernilai sama, salah satu harus hilang dari list, namun salah satu tetap di list
        wallet.tambahUangRupiah(20000);
        wallet.tambahUangRupiah(20000);
        wallet.ambilUang(20000);
        assertTrue(wallet.listUangLembaran.contains(20000), "Uang 20000 masih ada dalam list uang lembaran");

        wallet.tambahUangRupiah(200);
        wallet.tambahUangRupiah(200);
        wallet.ambilUang(200);
        assertTrue(wallet.listUangKoin.contains(200), "Uang 200 masih ada dalam list uang koin");

        //5. Ambil uang dengan jumlah negatif
        int uangTaken4 = wallet.ambilUang(-20000);
        assertEquals(0, uangTaken4, "Tidak dapat mengambil uang dengan jumlah negatif");
    }

    @Test
    void tampilkanUang() {
        Wallet wallet = new Wallet("Jake");
        wallet.tambahUangRupiah(500);
        wallet.tambahUangRupiah(100);
        wallet.tambahUangRupiah(200);
        wallet.tambahUangRupiah(50000);
        wallet.tambahUangRupiah(100000);
        wallet.tambahUangRupiah(20000);
        assertEquals(170800, wallet.tampilkanUang(), "Total uang harus sesuai dari list uang lembaran");
    }
}