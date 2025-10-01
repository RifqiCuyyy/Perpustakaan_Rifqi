package com.praktikum.testing.service;

import com.praktikum.testing.model.Anggota;
import com.praktikum.testing.model.Buku;
import com.praktikum.testing.repository.RepositoriBuku;

public class ServicePerpustakaan {
    private RepositoriBuku repositoriBuku;
    private KalkulatorDenda kalkulatorDenda;

    public ServicePerpustakaan(RepositoriBuku repositoriBuku, KalkulatorDenda kalkulatorDenda) {
        this.repositoriBuku = repositoriBuku;
        this.kalkulatorDenda = kalkulatorDenda;
    }

    public void pinjamBuku(Anggota anggota, String isbn) {
        if (anggota == null || isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("Anggota dan ISBN tidak boleh null atau kosong.");
        }

        if (!anggota.isAktif()) {
            throw new IllegalStateException("Anggota tidak aktif.");
        }

        if (!anggota.bolehPinjamLagi()) {
            throw new IllegalStateException("Anggota telah mencapai batas maksimal peminjaman.");
        }

        Buku buku = repositoriBuku.cariBerdasarkanIsbn(isbn);

        if (buku == null) {
            throw new IllegalArgumentException("Buku dengan ISBN " + isbn + " tidak ditemukan.");
        }

        if (!buku.isTersedia()) {
            throw new IllegalStateException("Buku sedang tidak tersedia.");
        }

        // Kurangi jumlah tersedia dan update di repositori
        buku.setJumlahTersedia(buku.getJumlahTersedia() - 1);
        repositoriBuku.simpan(buku);

        // Tambahkan buku ke daftar pinjaman anggota
        anggota.tambahBukuDipinjam(isbn);
    }
}