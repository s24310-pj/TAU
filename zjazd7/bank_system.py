class InsufficientFundsError(Exception):
    pass


class Account:
    def __init__(self, account_number: str, owner: str, initial_balance: float):
        self.account_number = account_number
        self.owner = owner
        self.balance = initial_balance

    def deposit(self, amount: float):
        if amount <= 0:
            raise ValueError("Kwota wpłaty musi być większa niż zero.")
        self.balance += amount

    def withdraw(self, amount: float):
        if amount > self.balance:
            raise InsufficientFundsError("Niewystarczające środki na koncie aby dokonać wypłaty.")
        self.balance -= amount

    async def transfer(self, to_account: "Account", amount: float):
        if amount > self.balance:
            raise InsufficientFundsError("Niewystarczające środki na koncie do wykonania przelewu.")
        self.withdraw(amount)
        to_account.deposit(amount)


class Bank:
    def __init__(self):
        self.accounts = {}

    def create_account(self, account_number: str, owner: str, initial_balance: float):
        if account_number in self.accounts:
            raise ValueError("Konto z tym numerem już istnieje.")
        account = Account(account_number, owner, initial_balance)
        self.accounts[account_number] = account
        return account

    def get_account(self, account_number: str):
        if account_number not in self.accounts:
            raise ValueError("Nie znaleziono konta o podanym numerze.")
        return self.accounts[account_number]

    async def process_transaction(self, transaction_func):
        await transaction_func()
