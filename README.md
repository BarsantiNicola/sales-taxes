# Sales taxes problem

- [Description](#description)
- [Installation](#installation)
- [User manual](#user-manual)
- [Developer manual](#developer-manual)

## Description

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.

Write an application that prints out the receipt details for these shopping baskets...

```
INPUT:

Input 1:
1 book at 12.49
1 music CD at 14.99
1 chocolate bar at 0.85

Input 2:
1 imported box of chocolates at 10.00
1 imported bottle of perfume at 47.50

Input 3:
1 imported bottle of perfume at 27.99
1 bottle of perfume at 18.99
1 packet of headache pills at 9.75
1 box of imported chocolates at 11.25
```

```
OUTPUT

Output 1:
1 book : 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83

Output 2:
1 imported box of chocolates: 10.50
1 imported bottle of perfume: 54.65
Sales Taxes: 7.65
Total: 65.15

Output 3:
1 imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 9.75
1 imported box of chocolates: 11.85
Sales Taxes: 6.70
Total: 74.68
```

## Installation

### Requirements

- [Maven 3.3.9+](https://maven.apache.org/download.cgi)

### Installation steps

```
git clone https://github.com/noir707/sales-taxes.git
cd sales-taxes

mvn clean package
```

## User manual

After installation steps:

- Copy `sales-taxes.jar` from `target` folder to your desired folder
- Copy `data` folder from `src/test/resources` to your desired folder
- In your desired folder run `java -jar sales-taxes.jar <receipt-file-path>`

### Configuration files

Configuration files are located in data folder.

`good_types.txt` is a semicolon separated file containing the good types.
- The first 'column' is the type name
- The second one is a flag which indicates if sales taxes should be applied to the goods of this type

`goods.txt` is a semicolon separated file containing the goods.
- The first 'column' is the good id
- The second 'column' is the good name
- The second 'column' is the type name. Each good can have 1 and only 1 type.

`taxes.properties` is a properties file containing the taxes rates.
- taxes.sales is the sales taxes rate
- taxes.import is the import taxes rate

## Developer manual

### Code styling
Line width in "Line Wrapper" and "Comments" tabs (Eclipse Window :arrow_forward: Preferences :arrow_forward: Java :arrow_forward: Code Style :arrow_forward: Formatter) should be set to 800.

### Packages structure
- Basic package is `cloud.nimvps.exercises.salestaxes`, which contains the main class
- Daos interfaces, implementations, models and parsers are located in `<basic_backage>.core.dao`
- Business logics classes are located in `<basic_backage>.core.logics` and processed objects models are in `<basic_backage>.core.model`
- Utility classes can be foud in `<basic_backage>.core.utils`
- View is located in `<basic_backage>.view` packege

### Notes
- No additional library was added to allow easier integration in any system
- New daos and parsers implementations can be used to read configurations and receipts from any source
- "Beware the groove!"