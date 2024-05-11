import re
import os

def escape_double_quotes(input_file):
    # Read the content of the file
    with open(input_file, 'r', encoding='utf-8') as file:
        content = file.read()

    # Add a backslash before double quote
    content = re.sub(r'(?<!\\)"', '\\"', content)

    content = content.replace('\n', '\\n')

    with open(input_file, 'w', encoding='utf-8') as file:
        file.write(content)

input_file_path = os.path.join(os.getcwd(), 'input.txt')

escape_double_quotes(input_file_path)
