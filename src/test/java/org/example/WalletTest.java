package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WalletTest {

    private Wallet myWallet;

    @BeforeEach
    void initMethod() {
        myWallet = new Wallet("Jake");
    }

    @AfterEach
    void cleanMethod() {
        myWallet = null;
    }


    @Test
    void testOwner() {
        myWallet.setOwner("Jay");
        assertEquals("Jay", myWallet.owner, "Owner wallet adalah Jay");
    }

    @Test
    void testTambahKartu() {
        myWallet.tambahKartu("Mastercard");
        myWallet.tambahKartu("Debit Card");
        assertAll(
                () -> assertNotEquals(0, myWallet.listKartu.size()),
                () -> assertEquals(2, myWallet.listKartu.size()),
                () -> assertTrue(myWallet.listKartu.contains("Mastercard")),
                () -> assertTrue(myWallet.listKartu.contains("Debit Card"))
        );
    }

    @Test
    void testAmbilKartu() {
        myWallet.tambahKartu("NPWP");
        myWallet.tambahKartu("VISA");

        myWallet.ambilKartu("NPWP");

        assertAll(
                () -> assertNull(myWallet.ambilKartu("NPWP"), "Kartu tidak dapat diambil karena sudah tidak ada dalam list kartu"),
                () -> assertTrue(myWallet.listKartu.contains("VISA"),"Kartu VISA masih ada di dalam wallet"),
                () -> assertFalse(myWallet.listKartu.contains("NPWP"),"Kartu Mastercard tidak boleh ada dalam list karena sudah diambil dari wallet"),
                () -> assertEquals(1, myWallet.listKartu.size())
        );
    }

    @Test
    void testTambahUangRupiah() {
        myWallet.tambahUangRupiah(500);
        myWallet.tambahUangRupiah(1000);
        myWallet.tambahUangRupiah(50000);

        assertAll(
                () -> assertTrue(myWallet.listUangLembaran.contains(50000), "50000 harus dimasukkan pada list uang lembaran"),
                () -> assertFalse(myWallet.listUangKoin.contains(50000), "50000 harus dimasukkan pada list uang lembaran"),
                () -> assertTrue(myWallet.listUangKoin.contains(500), "100 harus dimasukkan pada list uang koin"),
                () -> assertFalse(myWallet.listUangLembaran.contains(500), "500 harus dimasukkan pada list uang koin"),
                () -> assertEquals(2, myWallet.listUangKoin.size(), "Hanya dua elemen yang harus ada di list uang koin"),
                () -> assertEquals(1, myWallet.listUangLembaran.size(), "Hanya dua elemen yang harus ada di list uang koin"),

                () -> assertThrows(Error.class, () -> myWallet.tambahUangRupiah(-500), "Error saat uang yang ditambahkan kurang dari 0"),
                () -> assertDoesNotThrow(() -> myWallet.tambahUangRupiah(100), "Tidak boleh ada error saat menambah uang di atas 0")
        );
    }

    @Test
    void testAmbilUang() {
        myWallet.tambahUangRupiah(200);
        myWallet.tambahUangRupiah(1000);
        myWallet.tambahUangRupiah(5000);
        myWallet.tambahUangRupiah(5000);
        myWallet.tambahUangRupiah(20000);

        myWallet.ambilUang(20000);
        myWallet.ambilUang(5000);

        assertAll(
                () -> assertTrue(myWallet.listUangLembaran.contains(5000), "5000 yang tidak diambil tetap dalam list uang lembaran"),
                () -> assertFalse(myWallet.listUangLembaran.contains(20000), "20000 yang sudah diambil tidak dalam list uang lembaran"),
                () -> assertEquals(0, myWallet.ambilUang(20000), "Jumlah uang yang diambil harus sesuai dengan uang yang ada"),
                () -> assertEquals(0, myWallet.ambilUang(10000), "Uang yang diambil bernilai 0 karena tidak ada dalam list"),
                () -> assertEquals(0, myWallet.ambilUang(-20000), "Tidak dapat mengambil uang yang diambil bernilai kurang dari 0")
        );
    }

    @Test
    void tampilkanUang() {
        myWallet.tambahUangRupiah(500);
        myWallet.tambahUangRupiah(100);
        myWallet.tambahUangRupiah(200);
        myWallet.tambahUangRupiah(1000);
        myWallet.tambahUangRupiah(2000);
        myWallet.tambahUangRupiah(5000);
        myWallet.tambahUangRupiah(10000);
        myWallet.tambahUangRupiah(20000);
        myWallet.tambahUangRupiah(50000);
        myWallet.tambahUangRupiah(100000);

        myWallet.ambilUang(500);
        myWallet.ambilUang(1000);
        myWallet.ambilUang(10000);

        assertAll(
                () -> assertEquals(177300, myWallet.tampilkanUang(), "Total uang setelah inisialisasi harus sesuai"),
                () -> assertNotEquals(0, myWallet.tampilkanUang(), "Total uang setelah inisialisasi tidak boleh nol")
        );
    }
}