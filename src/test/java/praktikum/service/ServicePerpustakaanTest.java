package com.praktikum.testing.service;

import com.praktikum.testing.model.Anggota;
import com.praktikum.testing.model.Buku;
import com.praktikum.testing.repository.RepositoriBuku;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicePerpustakaanTest {

    @Mock
    private RepositoriBuku repositoriBukuMock;

    @Mock
    private KalkulatorDenda kalkulatorDendaMock;

    @InjectMocks
    private ServicePerpustakaan servicePerpustakaan;

    @Test
    @DisplayName("Test pinjam buku sukses")
    void testPinjamBuku_Sukses() {
        // Arrange
        Anggota anggota = new Anggota("A001", "John Doe", "john@test.com", "081234567890", Anggota.TipeAnggota.MAHASISWA);
        Buku buku = new Buku("12345", "JUnit 5 in Action", "John Doe", 1, 100000.0);

        when(repositoriBukuMock.cariBerdasarkanIsbn("12345")).thenReturn(buku);

        // Act
        servicePerpustakaan.pinjamBuku(anggota, "12345");

        // Assert
        assertEquals(0, buku.getJumlahTersedia());
        assertEquals(1, anggota.getJumlahBukuDipinjam());
        assertTrue(anggota.getIdBukuDipinjam().contains("12345"));
        verify(repositoriBukuMock, times(1)).simpan(buku);
    }

    @Test
    @DisplayName("Test pinjam buku gagal karena buku tidak ditemukan")
    void testPinjamBuku_BukuTidakDitemukan() {
        // Arrange
        Anggota anggota = new Anggota("A001", "John Doe", "john@test.com", "081234567890", Anggota.TipeAnggota.MAHASISWA);
        when(repositoriBukuMock.cariBerdasarkanIsbn("99999")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicePerpustakaan.pinjamBuku(anggota, "99999");
        });

        assertEquals("Buku dengan ISBN 99999 tidak ditemukan.", exception.getMessage());
    }
}