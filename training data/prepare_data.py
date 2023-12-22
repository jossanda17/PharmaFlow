import pandas as pd 
import datetime
import csv
import json
import os

def xlsx_to_csv(xlsx, csv):
    read_file = pd.read_excel(xlsx) 
    read_file.to_csv(csv, index = None, header=True)

def dataset_to_daftar_obat(dataset, daftar_obat, col_1, col_2):
    df = pd.read_csv(dataset, sep=',')
    df = df.drop_duplicates([col_1,col_2])[[col_1,col_2]]
    df.set_index(col_1).sort_values(by=[col_1]).to_csv(daftar_obat)

def generate_daily_to_csv(start_year, start_month, start_day, end_year, end_month, end_day, csvf):
    start = datetime.datetime(start_year,start_month,start_day)
    start = datetime.datetime.timestamp(start)
    end = datetime.datetime(end_year,end_month,end_day)
    end = datetime.datetime.timestamp(end)

    list=[]
    for day in range(int(start), int(end+86400), 86400):
        list.append(datetime.datetime.fromtimestamp(day).strftime("%Y-%m-%d"))

    with open(csvf, "w", newline="") as csvf:
        writer = csv.writer(csvf, delimiter=",")
        for day in list:
            writer.writerow([day])

def create_obat_daily_template(daftar_obat, daily, obat_daily_template):
    list_daftar_obat = []
    list_daily = []

    with open(daftar_obat) as csvf:
        reader = csv.reader(csvf, delimiter=",")
        next(reader)
        list_daftar_obat.extend(reader)

    with open(daily) as csvf:
        reader = csv.reader(csvf, delimiter=",")
        list_daily.extend(reader)

    with open(obat_daily_template, "w", newline="") as csvf:
        writer = csv.writer(csvf, delimiter=",")
        writer.writerow(["obat_id","obat_name","day","total_obat_amount"])

        for id in list_daftar_obat:
            for day in list_daily:
                writer.writerow([id[0],id[1],day[0],0])

def generate_each_obat_to_json(obat, daftar_obat, obat_daily_template):
    path_dir = os.path.join(os.getcwd(), "obat")
    if not os.path.exists(path_dir):
        os.makedirs(path_dir)

    list_id = []
    with open(daftar_obat) as csvf:
        reader = csv.reader(csvf, delimiter=",")
        next(reader)
        for item in reader:
            list_id.append(item[0])

    for i in list_id:
        list_obat = []
        list_amount = []

        with open(obat_daily_template) as csvf:
            reader = csv.reader(csvf, delimiter=',')
            
            for item in reader:
                    if item[0] == i:
                        list_obat.append(item)

        with open(obat) as csvf:
            reader = csv.reader(csvf, delimiter=',')

            for item in reader:
                if item[1] == i:
                    list_amount.append(item)

        for item in list_obat:
                for amount in list_amount:
                    if item[2] == amount[0]:
                        item[3] = amount[3]

        day_amount = {}
        dict = {}

        for rows in list_obat:
            day_amount[rows[2]] = int(rows[3])

        dict["obat_id"] = i
        dict["data"] = day_amount

        with open("./obat/"+i+".json", 'w') as jsonf:
            jsonf.write(json.dumps(dict, indent=4))


if __name__ == "__main__" :

    xlsx_to_csv("obat.xlsx", "obat.csv")

    dataset_to_daftar_obat("obat.csv", "daftar obat.csv", "obat_id", "obat_name")

    generate_daily_to_csv(2022,7,29, 2023,12,3, "daily.csv")

    create_obat_daily_template("daftar obat.csv", "daily.csv", "obat daily template.csv")

    generate_each_obat_to_json("obat.csv", "daftar obat.csv", "obat daily template.csv")

    os.remove("obat.csv")
    os.remove("daftar obat.csv")
    os.remove("daily.csv")
    os.remove("obat daily template.csv")