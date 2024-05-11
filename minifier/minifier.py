import os
import shutil
import subprocess
import pathlib

# Parent directory
release_dir = "release"

# CSS and JS parent dir
css_parent_dir = "css"
js_parent_dir = "js"

# Homepage, login, register CSS
homepage = "homepage"
login = "login"
register = "register"

output_css = "style.css"

# Files
application_js = "application.js"
script_js = "script.js"

# Original file
index_css_original = "../style.css"
login_css_original = "../login/style.css"

def minify(input_file, output_file):
    with open(input_file, 'r', encoding='utf-8') as file:
        content = file.read()

    content = content.replace("\n", "")

    print(content)

if __name__ == "__main__":
    # Create necessary directory

    if os.path.isdir(os.path.join(os.getcwd(), release_dir)):
        shutil.rmtree(os.path.join(os.getcwd(), release_dir))

    # Release directory
    os.mkdir(os.path.join(os.getcwd(), release_dir))

    # CSS and JS parent directory
    os.mkdir(os.path.join(os.getcwd(), release_dir, css_parent_dir))
    os.mkdir(os.path.join(os.getcwd(), release_dir, js_parent_dir))

    # Homepage, login, register CSS
    os.mkdir(os.path.join(os.getcwd(), release_dir, css_parent_dir, homepage))
    os.mkdir(os.path.join(os.getcwd(), release_dir, css_parent_dir, login))
    os.mkdir(os.path.join(os.getcwd(), release_dir, css_parent_dir, register))

    # Homepage, login, register JS
    os.mkdir(os.path.join(os.getcwd(), release_dir, js_parent_dir, homepage))
    os.mkdir(os.path.join(os.getcwd(), release_dir, js_parent_dir, login))
    os.mkdir(os.path.join(os.getcwd(), release_dir, js_parent_dir, register))

    print(os.path.join(pathlib.Path(os.getcwd()).parent.absolute(), index_css_original))   