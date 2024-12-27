# UAP
Daily Sceduler
- daily secduler adalah aplikasi desktop berbasis java yang dicarancang untuk membantu dalam meneglola tugas harian. Dengan aplikasi ini, pengguna dapat menambahkan,melihat,dan
  menghapus daftar tugas dengan grafis sederhana ini. Data tugas yang dimasukkan akan disimpan secara lokal di file tasks.txt, sehingga tidak hilang meskipun aplikasi ditutup.
Fitur:
-tambah tugas: pengguna dapat menmabhakan tugas baru dengan memasukkan judul,deskripsi,tanggal (YYYY-MM-DD), dan waktu (HH:MM).
-lihat daftar tugas: semua tugas yang telah ditambhakn akan ditampilkan dalam tabel dengan kolom (judul,deskripsi,tanggal,dan waktu).
-hapus tugas: pengguna dapat memilih tugas dari tabel untuk dihapus
-tombol "Clear Fielads" untuk membersihkan semua field input dengan sekali klik.
-semua tugas disimpan ke file tasks.txt di direktori yang sama dengan aplikasi. File ini dibuat otomatis jika belum ada. 
Cara Mengggunakan:
1. Menambahkan Tugas : Isi semua field input (Title, Description, Date Time). Klik tombol Add Task untuk menambahkan tugas ke daftar. Tugas akan mencul ditabel di bawah.
2. Mengahpus Tugas : Klik pada baris tugas yang ingin dihapus di table. Klik tombol Delete Task. Tugas akan dihapus dari daftar dan dari file tasks.txt
3. Mmebersihkan Input : Klik tombol Clear Fields untuk menghapus semua isi field input.
Sruktur File :
- DailySchedular.java : File utama berisi kode sumber aplikasi
- tasks.txt : File penyimpanan data tugas. File dibuat otomatis saat aplikasi deijalankan pertama kali. 
