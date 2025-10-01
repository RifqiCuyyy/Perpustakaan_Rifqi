package com.praktikum.testing.service;

import com.praktikum.testing.model.Peminjaman;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class KalkulatorDenda {

    private static final double TARIF_DENDA_PER_HARI = 1000.0;

    public double hitungDenda(Peminjaman peminjaman) {
        if (peminjaman == null || !peminjaman.isTerlambat()) {
            return 0.0;
        }

        LocalDate tanggalJatuhTempo = peminjaman.getTanggalJatuhTempo();
        LocalDate tanggalKembali = peminjaman.isSudahDikembalikan() ? peminjaman.getTanggalKembali() : LocalDate.now();

        if (tanggalKembali.isBefore(tanggalJatuhTempo) || tanggalKembali.isEqual(tanggalJatuhTempo)) {
            return 0.0;
        }

        long hariTerlambat = ChronoUnit.DAYS.between(tanggalJatuhTempo, tanggalKembali);
        return hariTerlambat * TARIF_DENDA_PER_HARI;
    }
}