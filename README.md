# Dự Án Tetris - Nhóm 5 (Lớp 25CNTT01)

Chào mừng bạn đến với dự án Game Tetris (Xếp gạch) kinh điển, được phát triển bởi Nhóm 5 lớp 25CNTT01. Dự án sử dụng ngôn ngữ Java và áp dụng mô hình kiến trúc MVC để quản lý mã nguồn hiệu quả.

**Giảng viên hướng dẫn:** TS. Nguyễn Hoàng Hải
## Thành viên nhóm
* **Nguyễn Lê Ngọc Phi** (Nhóm trưởng) - Thiết kế Giao diện (View) & Logic khối gạch (Model).
* **Trần Thuỳ Trinh** - Xử lý điều khiển (Controller) & Luồng chính (Main).
* **Phan Bảo Hân** - Xử lý âm thanh & Các tiện ích hệ thống (Utils & Resources).

---

##  Cấu trúc dự án (MVC)
Dự án được tổ chức theo các package rõ ràng để dễ dàng bảo trì và mở rộng:

* `src/model`: Chứa định nghĩa các khối gạch (I, J, L, O, S, T, Z) và logic di chuyển, xoay khối.
* `src/view`: Chứa mã nguồn thiết kế giao diện Game Board, bảng điểm và màu sắc.
* `src/controller`: Chứa bộ điều khiển lắng nghe sự kiện từ bàn phím.
* `src/utils`: Các lớp hỗ trợ đọc file, xử lý âm thanh `SoundManager.java`.
* `src/resources`: Chứa các tệp hình ảnh và âm thanh của trò chơi.

---

## Tính năng nổi bật
- [x] Giao diện chơi game trực quan, sinh động.
- [x] Hệ thống tính điểm và tăng tốc độ theo cấp độ.
- [x] Âm thanh nền và hiệu ứng âm thanh khi ăn điểm.
- [x] Tương thích tốt trên các nền tảng chạy Java.

---

## Hướng dẫn cài đặt
1.  **Clone dự án:**
    ```bash
    git clone [https://github.com/nguyenlengocphi-ship-it/Tetris_Nhom5.git](https://github.com/nguyenlengocphi-ship-it/Tetris_Nhom5.git)
    ```
2.  **Mở dự án:** Sử dụng VS Code
3.  **Chạy game:** Chạy file `Main.java` trong thư mục gốc.

---

## Cách điều khiển
* **Mũi tên Trái/Phải:** Di chuyển khối gạch sang ngang.
* **Mũi tên Lên:** Xoay khối gạch.
* **Mũi tên Xuống:** Tăng tốc độ rơi.
* **Phím Space:** Rơi ngay lập tức xuống đáy.

  ---

## Ảnh chụp màn hình (Screenshots)
*
