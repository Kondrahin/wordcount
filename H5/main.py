import re

# Функция для сортировки строк по числу вхождений
def sort_by_frequency(input_data):
    # Разбиваем строки по строкам
    lines = input_data.strip().split("\n")
    
    # Создаем список кортежей (строка, частота), извлекая число из каждой строки
    word_freq = []
    for line in lines:
        # Используем регулярное выражение для извлечения числа из строки
        match = re.search(r"(\d+)$", line)
        if match:
            freq = int(match.group(1))
            word_freq.append((line, freq))
    
    # Сортируем по частоте (по убыванию)
    word_freq.sort(key=lambda x: x[1], reverse=True)
    
    # Возвращаем отсортированные строки
    return "\n".join(line for line, _ in word_freq)

# Чтение строк из файла и сортировка
def process_file(input_filename, output_filename):
    try:
        # Открываем файл для чтения
        with open(input_filename, "r", encoding="utf-8") as infile:
            input_data = infile.read()
        
        # Сортируем строки по частоте
        sorted_data = sort_by_frequency(input_data)

        # Записываем результат в новый файл
        with open(output_filename, "w", encoding="utf-8") as outfile:
            outfile.write(sorted_data)
        
        print(f"Результат успешно записан в файл {output_filename}")
    except Exception as e:
        print(f"Произошла ошибка: {e}")

# Пример использования
input_filename = "/Users/a.kondrakhin/PycharmProjects/docker-hadoop/wordcount/H5/output/part-r-00000"  # Укажите путь к вашему входному файлу
output_filename = "sorted_output.txt"  # Укажите путь к выходному файлу

# Обрабатываем файл
process_file(input_filename, output_filename)

