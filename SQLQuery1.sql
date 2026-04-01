-- 1. Chọn Database đã có sẵn
USE tetris_db;
GO

-- 2. Tạo bảng lưu điểm (Nếu chưa có)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'highscores')
BEGIN
    CREATE TABLE highscores (
        id INT IDENTITY(1,1) PRIMARY KEY,
        player_name NVARCHAR(255) NOT NULL,
        score INT NOT NULL,
        play_date DATETIME DEFAULT GETDATE()
    );
END
GO
-- Thêm cột ngày giờ vào bảng hiện có
IF NOT EXISTS (SELECT * FROM sys.columns WHERE Name = 'play_date' AND Object_ID = Object_ID('highscores'))
BEGIN
    ALTER TABLE highscores ADD play_date DATETIME DEFAULT GETDATE();
END