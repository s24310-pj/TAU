import pytest
import asyncio
from unittest.mock import AsyncMock
from bank_system import Account, Bank, InsufficientFundsError


@pytest.fixture
def sample_account():
    return Account("123456", "Masny Bombel", 1000.0)


@pytest.fixture
def bank():
    return Bank()


def test_deposit(sample_account):
    sample_account.deposit(1000)
    assert sample_account.balance == 2000


def test_withdraw(sample_account):
    sample_account.withdraw(250)
    assert sample_account.balance == 750


def test_withdraw_insufficient_funds(sample_account):
    with pytest.raises(InsufficientFundsError):
        sample_account.withdraw(1500)


@pytest.mark.asyncio
async def test_transfer(sample_account):
    recipient = Account("654321", "Klient", 500.0)
    await sample_account.transfer(recipient, 300)
    assert sample_account.balance == 700
    assert recipient.balance == 800


@pytest.mark.asyncio
async def test_transfer_insufficient_funds(sample_account):
    recipient = Account("654321", "Klient", 500.0)
    with pytest.raises(InsufficientFundsError):
        await sample_account.transfer(recipient, 2000)


def test_create_account(bank):
    account = bank.create_account("123456", "Masny Bombel", 5000.0)
    assert account.account_number == "123456"
    assert account.owner == "Masny Bombel"
    assert account.balance == 5000.0


def test_create_duplicate_account(bank):
    bank.create_account("123456", "Masny Bombel", 1000.0)
    with pytest.raises(ValueError):
        bank.create_account("123456", "Klient", 2000.0)


def test_get_account(bank):
    bank.create_account("123456", "Masny Bombel", 1000.0)
    account = bank.get_account("123456")
    assert account.owner == "Masny Bombel"


def test_get_account_invalid(bank):
    with pytest.raises(ValueError):
        bank.get_account("654321")


@pytest.mark.asyncio
async def test_process_transaction(bank):
    account1 = bank.create_account("123456", "Masny Bombel", 1000.0)
    account2 = bank.create_account("654321", "Klient", 500.0)

    async def mock_transaction():
        await account1.transfer(account2, 300)

    await bank.process_transaction(mock_transaction)
    assert account1.balance == 700
    assert account2.balance == 800


@pytest.mark.asyncio
async def test_mocked_external_authorization():
    mock_authorization = AsyncMock(return_value=True)
    result = await mock_authorization()
    mock_authorization.assert_called_once()
    assert result is True
