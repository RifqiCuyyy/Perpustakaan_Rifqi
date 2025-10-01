package com.praktikum.testing.repository;

import com.praktikum.testing.model.Buku;
import java.util.List;

public interface RepositoriBuku {
    Buku cariBerdasarkanIsbn(String isbn);
    List<Buku> cariBerdasarkanJudul(String judul);
    List<Buku> semuaBuku();
    void simpan(Buku buku);
}