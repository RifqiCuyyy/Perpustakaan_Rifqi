package com.praktikum.testing.service;

import com.praktikum.testing.model.Peminjaman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KalkulatorDendaTest {

    private KalkulatorDenda kalkulatorDenda;

    @BeforeEach
    void setUp() {
        kalkulatorDenda = new KalkulatorDenda();
    }

    @Test
    @DisplayName("Test tidak ada denda jika dikembalikan tepat waktu")
    void testHitungDenda_TepatWaktu() {
        Peminjaman peminjaman = new Peminjaman();
        peminjaman.setTanggalJatuhTempo(LocalDate.now().plusDays(1));
        peminjaman.setSudahDikembalikan(false);

        assertEquals(0.0, kalkulatorDenda.hitungDenda(peminjaman));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5000.0",
            "1, 1000.0",
            "30, 30000.0"
    })
    @DisplayName("Test perhitungan denda dengan berbagai hari keterlambatan")
    void testHitungDenda_BerbagaiKeterlambatan(long hariTerlambat, double dendaDiharapkan) {
        Peminjaman peminjaman = new Peminjaman();
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(hariTerlambat);
        peminjaman.setTanggalJatuhTempo(tanggalJatuhTempo);
        peminjaman.setSudahDikembalikan(false);

        assertEquals(dendaDiharapkan, kalkulatorDenda.hitungDenda(peminjaman));
    }
}